package com.pgy.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.pgy.auth.bean.ExternalAuthResponse;

/**
 * Custom user details service.
 *
 * @author Felix
 */
public interface CustomUserDetailsService extends UserDetailsService {

    /**
     * Register user if not exist.
     *
     * @param user User to register.
     * @return Registered user details.
     */
    UserDetails registerUser(ExternalAuthResponse.User user);
}