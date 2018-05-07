package com.pgy.controller.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.pgy.controller.bean.RestResultResponse;
import com.pgy.controller.common.bean.UploadResult;

/**
 * Upload controller.
 *
 * @author Felix
 */
@RequestMapping("/api/v1")
public interface UploadController {

    @RequestMapping("/upload")
    RestResultResponse<UploadResult> upload(MultipartFile file) throws Exception;

}
