package com.pgy.controller.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Preconditions;
import com.pgy.auth.bean.CustomUser;
import com.pgy.common.DirUtils;
import com.pgy.common.LogMessageBuilder;
import com.pgy.common.ZipHelper;
import com.pgy.config.UploadConfig;
import com.pgy.controller.common.bean.UploadResult;
import com.pgy.material.bean.FileType;
import com.pgy.material.bean.MaterialStatus;
import com.pgy.rest.RestResponseFactory;
import com.pgy.rest.RestResultResponse;
import com.pgy.uploadlog.UploadLogManager;
import com.pgy.uploadlog.bean.UploadLog;
import com.pgy.util.bce.bos.BosFactory;
import com.pgy.util.bce.bos.BosUtil;
import com.pgy.util.bce.bos.bean.BosFileDescriptor;

/**
 * The impl of {@link UploadController}.
 *
 * @author Felix
 */
@RestController
public class UploadControllerImpl implements UploadController {
    private static final Log log = LogFactory.getLog(UploadControllerImpl.class);

    private final BosUtil bosUtil;
    private final UploadConfig uploadConfig;
    private final UploadLogManager uploadLogManager;

    @Autowired
    public UploadControllerImpl(BosFactory bosFactory, UploadConfig uploadConfig,
            UploadLogManager uploadLogManager) {
        this.bosUtil = bosFactory.getBosUtil();
        this.uploadConfig = uploadConfig;
        this.uploadLogManager = uploadLogManager;
    }

    @Override
    public RestResultResponse<UploadResult> upload(@AuthenticationPrincipal CustomUser loginUser,
            @RequestPart MultipartFile file) throws Exception {
        log.info(new LogMessageBuilder("upload.")
                .withParameter("file", file)
                .withParameter("fileName", file.getOriginalFilename())
                .build());

        Preconditions.checkNotNull(loginUser);
        // Check if file already exists, return the url if true.
        byte[] bytes = file.getBytes();
        String contentType = file.getContentType();
        String signature = calcSignature(bytes);
        UploadResult result;
        UploadLog oldUploadLog = uploadLogManager.detail(signature);
        if (oldUploadLog != null && StringUtils.isNotBlank(oldUploadLog.getUrl())) {
            result = UploadResult.Builder.anUploadResult()
                    .withUrl(oldUploadLog.getUrl())
                    .withDownloadUrl(oldUploadLog.getDownloadUrl())
                    .withMimeType(contentType)
                    .build();
            return RestResponseFactory.newSuccessfulResponse(result);
        }
        result = uploadFile(file.getOriginalFilename(), contentType, file.getBytes(), signature);

        uploadLogManager.create(UploadLog.Builder.anUploadLog()
                .withUploaderId(loginUser.getAccountId())
                .withFilename(file.getOriginalFilename())
                .withFileType(contentType)
                .withPath(result.getPath())
                .withUrl(result.getUrl())
                .withDownloadUrl(result.getDownloadUrl())
                .withSignature(signature)
                .withSize(result.getStorageUsed())
                .withStatus(MaterialStatus.ENABLED)
                .build());
        return RestResponseFactory.newSuccessfulResponse(result);
    }

    private UploadResult uploadFile(String filename, String contentType, byte[] fileContent, String signature)
            throws Exception {

        if (bosUtil != null && uploadConfig.getType() == UploadConfig.UploadType.BOS) {
            // TODO upload to bos, which is not supported now.
            BosFileDescriptor fileDescriptor = bosUtil.uploadWithDistinctName(filename,
                    new ByteArrayInputStream(fileContent), fileContent.length, contentType);
            URL url = bosUtil.populateUrl(fileDescriptor).getUrl();
            return UploadResult.Builder.anUploadResult()
                    .withUrl(url.toString())
                    .withMimeType(contentType)
                    .build();
        } else {
            String relativePath = signature + File.separator + filename;
            String downloadUrl = relativePath;
            String absolutePath = uploadConfig.getLocal().getDir() + File.separator + signature;
            int storageUsed = fileContent.length;
            File filePath = new File(absolutePath, filename);
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }

            absolutePath = absolutePath + File.separator + filename;
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(absolutePath);
                outputStream.write(fileContent);
                log.info("uploaded:" + absolutePath);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (FileType.parseStr(contentType) == FileType.ZIP) {
                String newPath = absolutePath + "_content";
                try {
                    storageUsed += ZipHelper.unzipToFile(absolutePath, newPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("Failed to unzip file.");
                }
                String indexFile = DirUtils.findFile(newPath, "index.html", true);
                if (StringUtils.isEmpty(indexFile)) {
                    throw new Exception("No index.html file exists in this zip.");
                }
                relativePath = relativePath + "_content" + indexFile.substring(newPath.length());
            }

            return UploadResult.Builder.anUploadResult()
                    .withPath(relativePath)
                    .withUrl(uploadConfig.getLocal().getPrefix() + relativePath)
                    .withDownloadUrl(uploadConfig.getLocal().getPrefix() + downloadUrl)
                    .withMimeType(contentType)
                    .withStorageUsed(storageUsed)
                    .build();
        }
    }

    private String calcSignature(byte[] data) {
        return DigestUtils.md2Hex(data);
    }

}
