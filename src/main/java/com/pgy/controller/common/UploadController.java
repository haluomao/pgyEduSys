package com.pgy.controller.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.pgy.auth.bean.CustomUser;
import com.pgy.controller.common.bean.UploadResult;
import com.pgy.rest.RestResultResponse;

/**
 * Upload controller.
 *
 * @author Felix
 */
@RequestMapping("/api/v1")
public interface UploadController {

    @RequestMapping("/upload")
    RestResultResponse<UploadResult> upload(CustomUser loginUser, MultipartFile file) throws Exception;

}
