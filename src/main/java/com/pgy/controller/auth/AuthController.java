package com.pgy.controller.auth;

import org.springframework.web.bind.annotation.RequestMapping;

import com.pgy.auth.bean.CustomUser;
import com.pgy.rest.RestResultResponse;

/**
 * The auth controller.
 *
 * @author Felix
 */
@RequestMapping("/")
public interface AuthController {

    @RequestMapping("api/v1/auth/logined")
    RestResultResponse<Boolean> checkLogin(CustomUser customUser) throws Exception;
}
