package com.pgy.auth;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Custom user detail interface.
 *
 * @author Felix
 */
public interface CustomUserDetails<T> extends UserDetails {

    long getUserId();

    void setUserId(long userId);

    T getRole();

    long getAccountId();
}
