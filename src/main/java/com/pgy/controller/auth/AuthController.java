package com.pgy.controller.auth;

import org.springframework.web.bind.annotation.RequestMapping;

import com.pgy.account.bean.Account;
import com.pgy.controller.bean.IdRequest;
import com.pgy.controller.bean.RestResultResponse;

/**
 * The auth controller.
 *
 * @author Felix
 */
@RequestMapping("/api/v1/auth")
public interface AuthController {

    @RequestMapping("/detail")
    RestResultResponse<Account> detail(IdRequest idRequest) throws Exception;
}
