package com.pgy.account;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.pgy.account.bean.Account;
import com.pgy.account.bean.AccountConfig;
import com.pgy.account.bean.AccountRequest;
import com.pgy.common.CollectionHelper;
import com.pgy.mapper.AccountConfigMapper;
import com.pgy.mapper.AccountMapper;

/**
 * The impl of {@link AccountManager}.
 *
 * @author Felix
 */
@Component
public class AccountManagerImpl implements AccountManager {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountConfigMapper accountConfigMapper;

    @Override
    public Account detail(long id) {
        return accountMapper.detail(id);
    }

    @Override
    public Account getAccount(String name) {
        return accountMapper.getAccount(name);
    }

    @Override
    public int create(Account account) {
        return accountMapper.create(account);
    }

    @Override
    public void delete(List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            accountMapper.delete(ids);
            accountConfigMapper.deleteByAccountId(ids);
        }
    }

    @Override
    public void updatePwd(Account account) {
        accountMapper.updatePwd(account);
    }

    @Override
    public void update(Account account) {
        accountMapper.update(account);
    }

    @Override
    public void updateStatus(List<Long> ids, int status) {
        accountMapper.updateStatus(ids, status);
    }

    @Override
    public List<Account> getAccounts(List<Long> ids) {
        return accountMapper.listByIds(ids);
    }

    @Override
    public List<Account> getAccountsByQuery(AccountRequest request) {
        return accountMapper.query(request);
    }

    @Override
    public boolean login(Account account) {
        return accountMapper.checkAccount(account.getAccountName(),
                account.getAccountPassword()) > 0;
    }

    @Override
    public int getAccountCount(long parentId, int role) {
        return accountMapper.countAccount(parentId, role);
    }

    @Override
    public boolean isExpired(Account account) {
        return new Date().after(account.getEndTime());
    }

    @Override
    public AccountConfig getAccountConfig(long accountId) {
        List<AccountConfig> configs = accountConfigMapper.listByAccountIds(Lists.newArrayList(accountId));
        return CollectionUtils.isNotEmpty(configs) ? configs.get(0) : null;
    }

    @Override
    public Map<Long, AccountConfig> getAccountConfigs(List<Long> accountIds) {
        return CollectionHelper.newHashMap(accountConfigMapper.listByAccountIds(accountIds),
                new Function<AccountConfig, Long>() {
                    @Override
                    public Long apply(AccountConfig input) {
                        return input.getAccountId();
                    }
                });
    }

    @Override
    public void updateAccountConfig(AccountConfig accountConfig) {
        accountConfigMapper.update(accountConfig);
    }

    @Override
    public void updateAccountStorage(AccountConfig accountConfig) {
        accountConfigMapper.updateStorage(accountConfig);
    }

    @Override
    public boolean isNameDuplicate(long id, String name) {
        Preconditions.checkArgument(StringUtils.isNotBlank(name));
        return accountMapper.getAccountCount(id, name) > 0;
    }
}
