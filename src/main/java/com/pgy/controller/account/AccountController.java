package com.pgy.controller.account;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.pgy.auth.bean.CustomUser;
import com.pgy.controller.account.bean.AccountVO;
import com.pgy.controller.bean.IdRequest;
import com.pgy.controller.bean.RestResultResponse;

/**
 * The account controller.
 *
 * @author Felix
 */
@RequestMapping("/api/v1/account")
public interface AccountController {

    @RequestMapping("/list")
    RestResultResponse<List<AccountVO>> listAccounts(CustomUser customUser) throws Exception;

    @RequestMapping("/detail")
    RestResultResponse<AccountVO> detail(CustomUser customUser, IdRequest request) throws Exception;

    @RequestMapping("/create")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    RestResultResponse<Long> create(CustomUser customUser, AccountVO accountVO) throws Exception;

    @RequestMapping("/update")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    RestResultResponse<Void> update(CustomUser customUser, AccountVO accountVO) throws Exception;

    @RequestMapping("/updatePwd")
    RestResultResponse<Void> updatePwd(CustomUser customUser, AccountVO accountVO) throws Exception;

    @RequestMapping("/delete")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    RestResultResponse<Void> delete(CustomUser customUser, IdRequest request) throws Exception;

}
