package com.pgy.controller.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

import com.pgy.auth.bean.CustomUser;
import com.pgy.rest.RestResponseFactory;
import com.pgy.rest.RestResultResponse;

/**
 * The auth controller impl.
 *
 * @author Felix
 */
@RestController
public class AuthControllerImpl implements AuthController {
    private static final Log log = LogFactory.getLog(AuthControllerImpl.class);

    @Override
    public RestResultResponse<Boolean> checkLogin(@AuthenticationPrincipal CustomUser customUser) throws Exception {
        if (customUser == null) {
            return RestResponseFactory.newFailedResponse(false);
        }
        return RestResponseFactory.newSuccessfulResponse(true);
    }

}
