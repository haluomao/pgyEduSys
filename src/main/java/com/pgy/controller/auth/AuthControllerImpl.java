package com.pgy.controller.auth;

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

    @Override
    public RestResultResponse<Boolean> checkLogin(@AuthenticationPrincipal CustomUser customUser) throws Exception {
        if (customUser == null) {
            return RestResponseFactory.newFailedResponse(false);
        }
        return RestResponseFactory.newSuccessfulResponse(true);
    }
}
