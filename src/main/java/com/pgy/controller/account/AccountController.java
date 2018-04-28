package com.pgy.controller.account;

import org.springframework.web.bind.annotation.RequestMapping;

import com.pgy.account.bean.Account;
import com.pgy.controller.bean.IdRequest;
import com.pgy.controller.bean.RestResultResponse;

/**
 * The account controller.
 *
 * @author Felix
 */
@RequestMapping("/api/account")
public interface AccountController {

    @RequestMapping("/detail")
    RestResultResponse<Account> detail(IdRequest idRequest) throws Exception;
}
