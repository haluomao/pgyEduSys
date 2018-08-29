package com.pgy.controller.account;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.pgy.mybatis.PgyTransactionHelper;
import com.pgy.rest.IdListRequest;
import com.pgy.rest.IdRequest;
import com.pgy.rest.RestResponseFactory;
import com.pgy.rest.RestResultResponse;

/**
 * The impl of {@link AccountController}.
 *
 * @author Felix
 */
@RestController
public class AccountControllerImpl implements AccountController {
    private static final Log log = LogFactory.getLog(AccountControllerImpl.class);
    private static final String USER_CREATE_LIMIT = "子账户数已达上限";
    private static final String USER_CONFIG_LIMIT = "家长账户配额已超出";
    private static final String STORAGE_EXCEED_LIMIT = "容量已超过额度";

    private final AccountManager accountManager;
    private final PgyTransactionHelper transactionHelper;

    @Autowired
    public AccountControllerImpl(AccountManager accountManager, PgyTransactionHelper transactionHelper) {
        this.accountManager = accountManager;
        this.transactionHelper = transactionHelper;
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

        long accountId = request.getId();
        Account account = accountManager.detail(accountId);
        AccountVO.Builder builder = AccountVO.Builder.anAccountVO().withAccount(account);
        if (account.getRole() == Role.ADMIN) {
            List<Account> teachers = accountManager.getAccountsByQuery(AccountRequest.Builder.anAccountRequest()
                    .withParentId(accountId)
                    .build());
            builder.withAccountConfig(accountManager.getAccountConfig(accountId))
                    .withTeacherCount(accountManager.getAccountCount(Lists.newArrayList(accountId),
                            Role.TEACHER.getValue()))
                    .withParentCount(accountManager.getAccountCount(CollectionHelper.transform(teachers,
                            new Function<Account, Long>() {
                                @Override
                                public Long apply(Account input) {
                                    return input.getId();
                                }
                            }),
                            Role.USER.getValue()));
        } else if (account.getRole() == Role.TEACHER) {
            builder.withAccountConfig(accountManager.getAccountConfig(accountId))
                    .withTeacherCount(0)
                    .withParentCount(accountManager.getAccountCount(Lists.newArrayList(accountId),
                            Role.USER.getValue()));
        }
        return RestResponseFactory.newSuccessfulResponse(builder.build());
    }

    @Override
    public RestResultResponse<Long> create(@AuthenticationPrincipal CustomUser customUser,
            @RequestBody final AccountVO accountVO) throws Exception {
        log.info(new LogMessageBuilder()
                .withMessage("create account")
                .withParameter("request", accountVO)
                .withParameter("loginUser", customUser)
                .build());
        if (accountManager.isNameDuplicate(accountVO.getId(), accountVO.getAccountName())) {
            return RestResponseFactory.newFailedResponse("用户名重复");
        }

        final AccountConfig accountConfig = accountManager.getAccountConfig(customUser.getAccountId());
        Preconditions.checkNotNull(accountConfig);

        String message = checkAccountLimit(accountConfig, accountVO.getRole(), accountVO.getParentLimit());
        if (StringUtils.isNotBlank(message)) {
            return RestResponseFactory.newFailedResponse(message);
        }
        message = checkStorageLimit(accountConfig, accountVO.getRole(), accountVO.getStorageLimit());
        if (StringUtils.isNotBlank(message)) {
            return RestResponseFactory.newFailedResponse(message);
        }

        accountVO.setParentId(customUser.getAccountId());
        accountVO.setStatus(AccountStatus.ENABLED);
        final Account account = accountVO.buildAccount();

        transactionHelper.runInTransaction(
                new Runnable() {
                    @Override
                    public void run() {
                        accountManager.create(account);
                        accountManager.createAccountConfig(accountVO.buildAccountConfig(account.getId()));
                        if (accountVO.getStorageLimit() > 0) {
                            accountConfig.setStorageUsed(accountConfig.getStorageUsed() + accountVO.getStorageLimit());
                            accountManager.updateAccountStorage(accountConfig);
                        }
                    }
                }
        );

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

        // delete cascaded accounts.
        List<Long> toDeleteIds = Lists.newArrayList(request.getId());
        if (customUser.getRole() == Role.SUPERADMIN) {
            List<Account> teachers = accountManager.getAccountsByQuery(AccountRequest.Builder.anAccountRequest()
                    .withParentId(request.getId())
                    .build());
            for (Account account : teachers) {
                toDeleteIds.add(account.getId());
                toDeleteIds.addAll(CollectionHelper.transform(
                        accountManager.getAccountsByQuery(AccountRequest.Builder.anAccountRequest()
                                .withParentId(request.getId())
                                .build()),
                        new Function<Account, Long>() {
                            @Override
                            public Long apply(Account input) {
                                return input.getId();
                            }
                        }));
            }
        } else if (customUser.getRole() == Role.ADMIN) {
            toDeleteIds.addAll(CollectionHelper.transform(
                    accountManager.getAccountsByQuery(AccountRequest.Builder.anAccountRequest()
                            .withParentId(request.getId())
                            .build()),
                    new Function<Account, Long>() {
                        @Override
                        public Long apply(Account input) {
                            return input.getId();
                        }
                    }));

            // recycle the storage.
            final AccountConfig childAccountConfig = accountManager.getAccountConfig(request.getId());
            Preconditions.checkNotNull(childAccountConfig);
            if (childAccountConfig.getStorageLimit() > 0) {
                final AccountConfig accountConfig = accountManager.getAccountConfig(customUser.getAccountId());
                accountConfig.setStorageUsed(accountConfig.getStorageUsed() - childAccountConfig.getStorageLimit());
                accountManager.updateAccountStorage(accountConfig);
            }
        }

        accountManager.delete(toDeleteIds);
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    @Override
    public RestResultResponse<Void> enable(@AuthenticationPrincipal CustomUser customUser,
            @RequestBody IdListRequest request) throws Exception {
        log.info(new LogMessageBuilder()
                .withMessage("enable account")
                .withParameter("request", request)
                .build());
        accountManager.updateStatus(request.getIdList(), AccountStatus.ENABLED.getValue());
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    @Override
    public RestResultResponse<Void> disable(@AuthenticationPrincipal CustomUser customUser,
            @RequestBody IdListRequest request) throws Exception {
        log.info(new LogMessageBuilder()
                .withMessage("disable account")
                .withParameter("request", request)
                .build());
        accountManager.updateStatus(request.getIdList(), AccountStatus.DISABLED.getValue());
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    private void checkParentId(long parentId, long accountId) {
        Account account = accountManager.detail(accountId);
        Preconditions.checkArgument(parentId == account.getParentId());
    }

    private String checkAccountLimit(AccountConfig accountConfig, Role newAccountRole, int parentLimit) {
        if (newAccountRole == Role.TEACHER && accountConfig.getTeacherLimit() >= 0) {
            // check teacher limit.
            int teacherCount = accountManager.getAccountCount(Lists.newArrayList(accountConfig.getAccountId()),
                    newAccountRole.getValue());
            if (teacherCount >= accountConfig.getTeacherLimit()) {
                return USER_CREATE_LIMIT;
            }

            // check user limit.
            List<Account> teachers = accountManager.getAccountsByQuery(AccountRequest.Builder.anAccountRequest()
                    .withParentId(accountConfig.getAccountId())
                    .build());
            int parentCount = accountManager.getAccountCount(CollectionHelper.transform(teachers,
                    new Function<Account, Long>() {
                        @Override
                        public Long apply(Account input) {
                            return input.getId();
                        }
                    }),
                    Role.USER.getValue());
            if (parentCount + parentLimit > accountConfig.getParentLimit()) {
                return USER_CONFIG_LIMIT;
            }
        } else if (newAccountRole == Role.USER && accountConfig.getParentLimit() >= 0) {
            int userCount = accountManager.getAccountCount(Lists.newArrayList(accountConfig.getAccountId()),
                    newAccountRole.getValue());
            return userCount < accountConfig.getParentLimit() ? StringUtils.EMPTY : USER_CREATE_LIMIT;
        }
        return StringUtils.EMPTY;
    }

    private String checkStorageLimit(AccountConfig accountConfig, Role newAccountRole, int storageLimit) {
        if (newAccountRole == Role.TEACHER && storageLimit > 0) {
            if (accountConfig.getStorageUsed() + storageLimit > accountConfig.getStorageLimit()) {
                return STORAGE_EXCEED_LIMIT;
            }
        }
        return StringUtils.EMPTY;
    }
}
