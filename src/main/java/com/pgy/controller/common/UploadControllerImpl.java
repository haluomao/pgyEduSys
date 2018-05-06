package com.pgy.controller.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pgy.common.LogMessageBuilder;
import com.pgy.config.UploadConfig;
import com.pgy.controller.bean.RestResultResponse;
import com.pgy.controller.common.bean.UploadResult;
import com.pgy.rest.RestResponseFactory;
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

    @Autowired
    public UploadControllerImpl(BosFactory bosFactory, UploadConfig uploadConfig) {
        this.bosUtil = bosFactory.getBosUtil();
        this.uploadConfig = uploadConfig;
    }

    @Override
    public RestResultResponse<UploadResult> upload(@RequestPart MultipartFile file) throws Exception {
        log.info(new LogMessageBuilder("upload.")
                .withParameter("fileName", file)
                .build());
        UploadResult result = uploadFile(file.getOriginalFilename(),
                file.getContentType(), file.getBytes());
        return RestResponseFactory.newSuccessfulResponse(result);
    }

    private UploadResult uploadFile(String filename, String contentType, byte[] fileContent) {
        if (bosUtil != null && uploadConfig.getType() == UploadConfig.UploadType.BOS) {
            BosFileDescriptor fileDescriptor = bosUtil.uploadWithDistinctName(filename,
                    new ByteArrayInputStream(fileContent), fileContent.length, contentType);
            URL url = bosUtil.populateUrl(fileDescriptor).getUrl();
            return UploadResult.Builder.anUploadResult()
                    .withUrl(url.toString())
                    .withMimeType(contentType)
                    .build();
        } else {
            String path = uploadConfig.getLocal().dir;
            File filePath = new File(path, filename);
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            path = path + File.separator + filename;
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(path);
                outputStream.write(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return UploadResult.Builder.anUploadResult()
                    .withUrl(uploadConfig.getLocal().prefix + path)
                    .withMimeType(contentType)
                    .build();
        }
    }
}
