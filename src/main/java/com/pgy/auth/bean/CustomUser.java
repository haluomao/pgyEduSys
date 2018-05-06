package com.pgy.auth.bean;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.pgy.account.bean.Account;
import com.pgy.auth.CustomUserDetails;

/**
 * Custom user detail adding id property.
 *
 * @author Felix
 */
public class CustomUser extends User implements CustomUserDetails<Role> {

    private long userId;
    private Role role;
    private long accountId;

    public CustomUser(Account account) {
        super(account.getAccountName(), account.getAccountPassword(), Collections.singletonList(new
                SimpleGrantedAuthority("ROLE_" + account.getRole())));
        this.accountId = account.getId();
        this.role = account.getRole();
        if (this.role == Role.USER) {
            this.userId = this.accountId;
        }
    }

    public CustomUser(long userId, String username, String password, Collection<? extends GrantedAuthority>
            authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public CustomUser(long userId, String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority>
            authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public long getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{CustomUser, userId=");
        builder.append(userId);
        builder.append(", role=");
        builder.append(role);
        builder.append(", accountId=");
        builder.append(accountId);
        builder.append("}");
        return builder.toString();
    }
}
