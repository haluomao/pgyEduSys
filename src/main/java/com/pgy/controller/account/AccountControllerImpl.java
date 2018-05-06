package com.pgy.controller.account;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.pgy.account.AccountManager;
import com.pgy.account.bean.Account;
import com.pgy.account.bean.AccountConfig;
import com.pgy.account.bean.AccountRequest;
import com.pgy.account.bean.AccountStatus;
import com.pgy.auth.bean.CustomUser;
import com.pgy.auth.bean.Role;
import com.pgy.common.CollectionHelper;
import com.pgy.common.LogMessageBuilder;
import com.pgy.controller.account.bean.AccountVO;
import com.pgy.controller.bean.IdRequest;
import com.pgy.controller.bean.RestResultResponse;
import com.pgy.rest.RestResponseFactory;

/**
 * The impl of {@link AccountController}.
 *
 * @author Felix
 */
@RestController
public class AccountControllerImpl implements AccountController {
    private static final Log log = LogFactory.getLog(AccountControllerImpl.class);

    private final AccountManager accountManager;

    @Autowired
    public AccountControllerImpl(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public RestResultResponse<List<AccountVO>> listAccounts(@AuthenticationPrincipal CustomUser customUser)
            throws Exception {
        log.info(new LogMessageBuilder()
                .withMessage("list account")
                .withParameter("customUser", customUser)
                .build());
        List<Account> accounts = accountManager.getAccountsByQuery(
                AccountRequest.Builder.anAccountRequest()
                        .withParentId(customUser.getAccountId())
                        .build());
        final Map<Long, AccountConfig> accountIdConfigMap = accountManager.getAccountConfigs(
                CollectionHelper.transform(accounts,
                        new Function<Account, Long>() {
                            @Override
                            public Long apply(Account input) {
                                return input.getId();
                            }
                        }));
        return RestResponseFactory.newSuccessfulResponse(
                CollectionHelper.transform(accounts,
                        new Function<Account, AccountVO>() {
                            @Override
                            public AccountVO apply(Account input) {
                                return AccountVO.Builder.anAccountVO()
                                        .withAccount(input)
                                        .withConfig(accountIdConfigMap.get(input.getId()))
                                        .build();
                            }
                        })
        );
    }

    @Override
    public RestResultResponse<AccountVO> detail(@AuthenticationPrincipal CustomUser customUser,
            @RequestBody IdRequest request) throws Exception {
        log.info(new LogMessageBuilder()
                .withMessage("detail account")
                .withParameter("customUser", customUser)
                .withParameter("request", request)
                .build());
        long accountId = customUser.getRole().isAdmin() ? request.getId() : customUser.getAccountId();
        Account account = accountManager.detail(accountId);
        AccountVO.Builder builder = AccountVO.Builder.anAccountVO().withAccount(account);
        if (account.getRole() == Role.ADMIN) {
            builder.withConfig(accountManager.getAccountConfig(accountId))
                    .withTeacherCount((long) accountManager.getAccountCount(accountId, Role.TEACHER.getValue()))
                    .withParentCount((long) accountManager.getAccountCount(accountId, Role.USER.getValue()));
        }
        return RestResponseFactory.newSuccessfulResponse(builder.build());
    }

    @Override
    public RestResultResponse<Long> create(@AuthenticationPrincipal CustomUser customUser,
            @RequestBody AccountVO accountVO) throws Exception {
        log.info(new LogMessageBuilder()
                .withMessage("create account")
                .withParameter("request", accountVO)
                .build());

        checkLimit(customUser.getAccountId(), accountVO.getRole());

        accountVO.setParentId(customUser.getAccountId());
        accountVO.setStatus(AccountStatus.ENABLED);
        Account account = accountVO.buildAccount();

        accountManager.create(account);
        return RestResponseFactory.newSuccessfulResponse(account.getId());
    }

    @Override
    public RestResultResponse<Void> update(@AuthenticationPrincipal CustomUser customUser,
            @RequestBody AccountVO accountVO) throws Exception {
        log.info(new LogMessageBuilder()
                .withMessage("update account")
                .withParameter("request", accountVO)
                .build());
        Account account = accountVO.buildAccount();
        accountManager.update(account);
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    @Override
    public RestResultResponse<Void> updatePwd(@AuthenticationPrincipal CustomUser customUser,
            @RequestBody AccountVO accountVO) throws Exception {
        log.info(new LogMessageBuilder()
                .withMessage("update account pwd")
                .withParameter("request", accountVO)
                .build());
        Account account = accountVO.buildAccount();
        accountManager.updatePwd(account);
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    @Override
    public RestResultResponse<Void> delete(@AuthenticationPrincipal CustomUser customUser,
            @RequestBody IdRequest request) throws Exception {
        log.info(new LogMessageBuilder()
                .withMessage("delete account")
                .withParameter("request", request)
                .build());
        checkParentId(customUser.getAccountId(), request.getId());
        accountManager.delete(Lists.newArrayList(request.getId()));
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    private void checkParentId(long parentId, long accountId) {
        Account account = accountManager.detail(accountId);
        Preconditions.checkArgument(parentId == account.getParentId());
    }

    private void checkLimit(long parentId, Role newAccountRole) {
        AccountConfig accountConfig = accountManager.getAccountConfig(parentId);
        if (newAccountRole == Role.TEACHER) {
            int teacherCount = accountManager.getAccountCount(parentId, newAccountRole.getValue());
            Preconditions.checkArgument(teacherCount < accountConfig.getTeacherLimit());
        } else if (newAccountRole == Role.USER) {
            int teacherCount = accountManager.getAccountCount(parentId, newAccountRole.getValue());
            Preconditions.checkArgument(teacherCount < accountConfig.getParentLimit());
        }
    }
}
