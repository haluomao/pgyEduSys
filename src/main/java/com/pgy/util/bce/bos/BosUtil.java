package com.pgy.util.bce.bos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baidubce.BceServiceException;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.baidubce.services.bos.model.ListObjectsRequest;
import com.baidubce.services.bos.model.ListObjectsResponse;
import com.baidubce.services.bos.model.ObjectMetadata;
import com.baidubce.services.bos.model.PutObjectRequest;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.pgy.common.Constant;
import com.pgy.common.LogMessageBuilder;
import com.pgy.util.bce.bos.bean.BosConfig;
import com.pgy.util.bce.bos.bean.BosFileDescriptor;

/**
 * Implementation of {@link BosUtil}.
 *
 * @author Felix
 */
public class BosUtil {

    private static final Log log = LogFactory.getLog(BosUtil.class);

    private static final int NO_EXPIRATION = -1;
    private static final String FILE_SEPARATOR = ".";
    private static final String PATH_SEPARATOR = "/";
    private static final String BOS_PROTOCOL = "bos://";
    private static final String BASE_PATH = "/";

    private BosClient bosClient;
    private String bucket;
    private String uploadDir;
    private String tempDir;

    public BosUtil(BosClientProvider bosClientProvider, BosConfig bosConfig) {
        bosClient = bosClientProvider.createBosClient();
        bucket = bosConfig.getBucket();
        uploadDir = bosConfig.getUploadDir();
        tempDir = bosConfig.getTempDir();
    }

    /**
     * Upload file with given name.
     *
     * @param fileName The file name.
     * @param file File
     * @return {@link BosFileDescriptor} with no url populated.
     */
    public BosFileDescriptor upload(String fileName, File file) {
        return upload(new PutObjectRequest(bucket, buildBosFileName(StringUtils.EMPTY, fileName, false), file));
    }

    /**
     * Upload file with given name to given dir.
     *
     * @param dir Target dir.
     * @param fileName Given filename.
     * @param file File.
     * @return {@link BosFileDescriptor} with no url populated.
     */
    public BosFileDescriptor upload(String dir, String fileName, File file) {
        return upload(new PutObjectRequest(bucket, buildBosFileName(dir, fileName, false), file));
    }

    public BosFileDescriptor uploadWithOriginalName(String fileName, File file) {
        return upload(new PutObjectRequest(bucket, buildBosFileName(uploadDir, fileName, false), file));
    }

    /**
     * Upload file with global distinct name to upload dir.
     *
     * @param fileName Original file name.
     * @param file File.
     * @return {@link BosFileDescriptor} with no url populated.
     */
    public BosFileDescriptor uploadWithDistinctName(String fileName, File file) {
        return upload(new PutObjectRequest(bucket, buildBosFileName(uploadDir, fileName, true), file));
    }

    /**
     * Upload stream with custom header.
     *
     * @param fileName Original file name of stream source.
     * @param inStream Input stream of file.
     * @param meta Metadata for http header of uploadWithDistinctName request.
     * @return {@link BosFileDescriptor} with no url populated.
     */
    public BosFileDescriptor uploadWithDistinctName(String fileName, InputStream inStream, ObjectMetadata meta) {
        return upload(new PutObjectRequest(bucket, buildBosFileName(uploadDir, fileName, true), inStream, meta));
    }

    /**
     * Upload stream with info like length and mime type.
     *
     * @param fileName Original file name of stream source.
     * @param inStream Input stream of file.
     * @param contentLength For Content-Length in http header.
     * @param contentType For Content-Type in http header.
     * @return {@link BosFileDescriptor} with no url populated.
     */
    public BosFileDescriptor uploadWithDistinctName(String fileName, InputStream inStream, long contentLength,
            String contentType) {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(contentLength);
        meta.setContentType(contentType);
        return uploadWithDistinctName(fileName, inStream, meta);
    }

    private String buildBosFileName(String dir, String fileName, boolean needGenerate) {
        String key = genKey(fileName, needGenerate);
        if (StringUtils.isNotBlank(dir)) {
            return StringUtils.join(new String[] { dir, key }, PATH_SEPARATOR);
        } else {
            return key;
        }
    }

    /**
     * Populate url for descriptor.
     *
     * @param descriptor Bos file descriptor. Id is needed.
     * @return Request parameter with url populated. Url will not expire.
     */
    public BosFileDescriptor populateUrl(BosFileDescriptor descriptor) {
        return populateUrl(descriptor, NO_EXPIRATION);
    }

    /**
     * Populate url for descriptor.
     *
     * @param descriptor Bos file descriptor. Id is needed.
     * @param expirationSecs Seconds before expiration.
     * @return Request parameter with url populated.
     */
    public BosFileDescriptor populateUrl(BosFileDescriptor descriptor, int expirationSecs) {
        Preconditions.checkNotNull(descriptor);
        Preconditions.checkNotNull(descriptor.getId());
        URL url = bosClient.generatePresignedUrl(bucket, descriptor.getId(), expirationSecs);
        descriptor.setUrl(removeSignature(url));
        return descriptor;
    }

    /**
     * Delete file from bos. {@link com.baidubce.BceClientException BceClientException} and {@link
     * com.baidubce.BceServiceException BceServerException} indicate failure.
     *
     * @param descriptor Bos file descriptor. Id is needed.
     */
    public void delete(BosFileDescriptor descriptor) {
        bosClient.deleteObject(bucket, descriptor.getId());
    }

    /**
     * Get BOS URL like "bos://bucket/file".
     *
     * @param descriptor Bos file descriptor. Id is needed.
     * @return The BOS URL.
     */
    public String getBosUrl(BosFileDescriptor descriptor) {
        return formatBosProtocol(descriptor.getId());
    }

    /**
     * Delete dir from bos.
     *
     * @param prefix The file prefix.
     */
    public void deleteDir(String prefix) {
        Preconditions.checkNotNull(prefix);
        if (!prefix.endsWith(Constant.SLASH)) {
            prefix = prefix + Constant.SLASH;
        }
        ListObjectsResponse response = bosClient.listObjects(bucket, prefix);
        while (true) {
            for (BosObjectSummary objectSummary : response.getContents()) {
                try {
                    bosClient.deleteObject(bucket, objectSummary.getKey());
                } catch (BceServiceException e) {
                    log.warn("Specific key can not be deleted: " + objectSummary.getKey(), e);
                }
            }
            if (response.getNextMarker() != null) {
                response = bosClient.listObjects(bucket, prefix);
            } else {
                break;
            }
        }
    }

    /**
     * Delete dir from bos quietly.
     *
     * @param prefix The file prefix.
     */
    public void deleteDirQuietly(String prefix) {
        try {
            deleteDir(prefix);
        } catch (Exception e) {
            log.error(new LogMessageBuilder("Error occurred while delete dir.")
                    .withParameter("prefix", prefix), e);
        }
    }

    /**
     * Get BOS URL.
     *
     * @return
     */
    public String formatBosProtocol(String path) {
        return BOS_PROTOCOL + bucket + FilenameUtils.concat(BASE_PATH, path);
    }

    /**
     * Remove BOS URL
     */
    public String removeBosProtocal(String path) {
        return path.substring(BOS_PROTOCOL.length() + bucket.length() + BASE_PATH.length());
    }

    /**
     * List all bos files.
     *
     * @param prefix The file name prefix.
     * @return All files with prefix.
     */
    public List<String> list(String prefix) {
        ListObjectsResponse response = bosClient.listObjects(bucket, prefix);
        return Lists.newArrayList(Lists.transform(response.getContents(), new Function<BosObjectSummary, String>() {
            @Override
            public String apply(BosObjectSummary input) {
                return input.getKey();
            }
        }));
    }

    /**
     * List all bos files, consider limit.
     *
     * @param prefix The file name prefix.
     * @param showAll If true, show all files, if false, the result will be limited by default 1000.
     * @return All files with prefix.
     */
    public List<String> list(String prefix, boolean showAll) {
        List<String> fileList = Lists.newArrayList();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucket);
        listObjectsRequest.setPrefix(prefix);
        while (true) {
            ListObjectsResponse response = bosClient.listObjects(listObjectsRequest);
            fileList.addAll(Lists.transform(response.getContents(), new Function<BosObjectSummary, String>() {
                @Override
                public String apply(BosObjectSummary input) {
                    return input.getKey();
                }
            }));

            if (showAll && response.getNextMarker() != null) {
                listObjectsRequest.setMarker(response.getNextMarker());
            } else {
                break;
            }
        }
        return fileList;
    }

    /**
     * List all bos sub dir.
     *
     * @param prefix The parent dir name prefix, format must be xxx/yyy/, the final '/' is needed.
     * @param showAll If true, show all subdir, if false, the result will be limited by 1000.
     * @return All the subdirs in parent dir.
     */
    public List<String> listSubDir(String prefix, boolean showAll) {
        Preconditions.checkNotNull(prefix);
        List<String> dirList = Lists.newArrayList();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucket);
        listObjectsRequest.setDelimiter(Constant.SLASH);

        if (!prefix.endsWith(Constant.SLASH)) {
            listObjectsRequest.setPrefix(prefix + Constant.SLASH);
        }

        while (true) {
            ListObjectsResponse response = bosClient.listObjects(listObjectsRequest);
            dirList.addAll(Lists.transform(response.getCommonPrefixes(), new Function<String, String>() {
                @Override
                public String apply(String input) {
                    String[] keys = input.split(Constant.SLASH);
                    return keys[keys.length - 1];
                }
            }));

            if (showAll && response.getNextMarker() != null) {
                listObjectsRequest.setMarker(response.getNextMarker());
            } else {
                break;
            }
        }
        return dirList;
    }

    /**
     * Read bos file.
     * @param key The file key.
     * @return The content.
     */
    public byte[] readBosFile(String key) {
        return bosClient.getObjectContent(bucket, key);
    }

    /**
     * Check file exist or not.
     * @param path The file path on bos.
     * @return True if file exist.
     */
    public boolean fileExist(String path) {
        Preconditions.checkNotNull(path);

        return list(path).size() > 0;
    }

    /**
     * Get available bos path.
     * @param candidatePaths The
     * @return The bos paths where file is exist.
     */
    public List<String> getAvailableBosPath(List<String> candidatePaths) {
        Preconditions.checkNotNull(candidatePaths);

        List<String> availableFilePath = Lists.newArrayList();
        for (String path : candidatePaths) {
            String tempPath = path;
            if (path.startsWith(BOS_PROTOCOL)) {
                tempPath = removeBosProtocal(tempPath);
            }
            if (tempPath.endsWith("*")) {
                tempPath = tempPath.substring(0, tempPath.length() - 1);
            }
            if (fileExist(tempPath)) {
                availableFilePath.add(path);
            }
        }
        return availableFilePath;
    }

    /**
     * Compare the local file and bos file content.
     *
     * @param localFilePath The local file path.
     * @param bosFilePath The bos file path.
     * @return True if same, otherwise false.
     */
    public boolean compareFileContent(String localFilePath, String bosFilePath) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(localFilePath));
        Preconditions.checkArgument(StringUtils.isNotEmpty(bosFilePath));

        if (!fileExist(bosFilePath)) {
            return false;
        }

        File localFile = new File(localFilePath);
        if (!localFile.exists()) {
            return false;
        }

        try {
            byte[] bosFileContent = readBosFile(bosFilePath);
            byte[] localFileContent = FileUtils.readFileToByteArray(localFile);

            return Arrays.equals(bosFileContent, localFileContent);
        } catch (Exception e) {
            log.error(new LogMessageBuilder("Error occurred when compare file content")
                    .withParameter("localFilePath", localFilePath)
                    .withParameter("bosFilePath", bosFilePath)
                    .build(), e);
        }

        return false;
    }

    /**
     * Check file in prefix exist and content is not empty.
     *
     * @param prefix The prefix.
     * @return True if file exist and not empty.
     */
    public boolean fileExistAndContentNotEmpty(String prefix) throws FileNotFoundException {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucket);
        listObjectsRequest.setPrefix(prefix);
        boolean notFoundFile = true;
        while (true) {
            ListObjectsResponse response = bosClient.listObjects(listObjectsRequest);
            if (CollectionUtils.isNotEmpty(response.getContents())) {
                for (BosObjectSummary bosObjectSummary : response.getContents()) {
                    if (bosObjectSummary.getSize() != 0) {
                        return true;
                    }
                    if (!bosObjectSummary.getKey().endsWith(Constant.SLASH)) {
                        notFoundFile = false;
                    }
                }
            }
            if (response.getNextMarker() != null) {
                listObjectsRequest.setMarker(response.getNextMarker());
            } else {
                break;
            }
        }
        if (notFoundFile) {
            throw new FileNotFoundException("There is not file in " + prefix);
        }
        return false;
    }

    public String buildTempDir() {
        if (StringUtils.isEmpty(tempDir)) {
            return UUID.randomUUID().toString();
        }
        String datePath = FilenameUtils.concat(tempDir, DateFormatUtils.format(new Date(), Constant.DATE_PATTERN));
        return FilenameUtils.concat(datePath, UUID.randomUUID().toString());
    }

    private BosFileDescriptor upload(PutObjectRequest request) {
        bosClient.putObject(request);
        return new BosFileDescriptor.Builder()
                .withId(request.getKey())
                .build();
    }

    private String genKey(String fileName, boolean needGenerate) {
        if (!needGenerate) {
            return fileName;
        }
        String ext = FilenameUtils.getExtension(fileName);
        return StringUtils.join(new String[] { UUID.randomUUID().toString(), ext }, FILE_SEPARATOR);
    }

    private URL removeSignature(URL url) {
        String urlStr = url.toString();
        String query = url.getQuery();
        if (StringUtils.isNotBlank(query)) {
            urlStr = urlStr.replace("?" + query, "");
        }
        try {
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            log.error(new LogMessageBuilder("Cannot remove signature.")
                    .withParameter("url", url)
                    .build());
        }
        return null;
    }
}
