package com.pgy.account;

import java.util.List;
import java.util.Map;

import com.pgy.account.bean.Account;
import com.pgy.account.bean.AccountConfig;
import com.pgy.account.bean.AccountRequest;

/**
 * The account manager.
 *
 * @author Felix
 */
public interface AccountManager {
    long ROOT_ID = 1L;

    Account detail(long id);

    Account getAccount(String name);

    int create(Account account);

    void delete(List<Long> ids);

    void updatePwd(Account account);

    void update(Account account);

    void updateStatus(List<Long> ids, int status);

    List<Account> getAccounts(List<Long> ids);

    List<Account> getAccountsByQuery(AccountRequest request);

    boolean login(Account account);

    int getAccountCount(List<Long> parentIds, int role);

    boolean isExpired(Account account);

    AccountConfig getAccountConfig(long accountId);

    Map<Long, AccountConfig> getAccountConfigs(List<Long> accountIds);

    void createAccountConfig(AccountConfig accountConfig);

    void updateAccountStorage(AccountConfig accountConfig);

    boolean isNameDuplicate(long id, String name);
}
