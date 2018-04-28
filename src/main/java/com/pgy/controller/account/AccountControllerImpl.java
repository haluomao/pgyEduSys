package com.pgy.controller.account;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RestController;

import com.pgy.account.bean.Account;
import com.pgy.common.LogMessageBuilder;
import com.pgy.controller.bean.IdRequest;
import com.pgy.controller.bean.RestResultResponse;

/**
 * The impl of {@link AccountController}.
 *
 * @author Felix
 */
@RestController
public class AccountControllerImpl implements AccountController {
    private static final Log log = LogFactory.getLog(AccountControllerImpl.class);

    @Override
    public RestResultResponse<Account> detail(IdRequest idRequest) throws Exception {
        log.info(new LogMessageBuilder()
                .withMessage("detail")
                .withParameter("idRequest", idRequest)
                .build());

        return null;
    }
}
