package com.pgy.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.pgy.account.AccountManager;
import com.pgy.account.bean.Account;
import com.pgy.auth.bean.CustomUser;
import com.pgy.auth.bean.ExternalAuthResponse;

/**
 * Custom service providing user details from database.
 *
 * @author Felix
 */
@Component
public class DbUserDetailsService implements CustomUserDetailsService {

    private final AccountManager accountManager;

    @Autowired
    public DbUserDetailsService(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountManager.getAccount(username);
        if (account == null || !account.isEnabled()) {
            throw new UsernameNotFoundException("No such user");
        }
        return new CustomUser(account);
    }

    @Override
    public UserDetails registerUser(ExternalAuthResponse.User user) {
        return new CustomUser(null);
    }
//
//    private AccountType buildAccountType(ExternalAuthResponse.User user) {
//        return AccountType.Builder.anAccountType()
//                .withAccountName(user.getName())
//                .withAccountPass(StringUtils.EMPTY)
//                .withRole(Role.valueOf(user.getAdRole()))
//                .withStatus(AccountStatus.ENABLED)
//                .withContact(StringUtils.EMPTY)
//                .withPhone(StringUtils.EMPTY)
//                .withCompany(StringUtils.EMPTY)
//                .build();
//    }
}
