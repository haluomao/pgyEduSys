package com.pgy.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pgy.account.bean.Account;
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

    @Override
    public Account detail(long id) {
        return accountMapper.detail(id);
    }

    @Override
    public int create(Account account) {
        return 0;
    }
}
