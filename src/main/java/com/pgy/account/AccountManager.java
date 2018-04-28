package com.pgy.account;

import com.pgy.account.bean.Account;

/**
 * The account manager.
 *
 * @author Felix
 */
public interface AccountManager {
    Account detail(long id);

    int create(Account account);
}
