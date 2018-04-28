package com.pgy.auth;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

/**
 * Auth config.
 *
 * @author Felix
 */
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthConfig {

    public static class Auth {
        private String username;
        private String password;
        private String role;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    public enum AuthType {
        INMEM,
        INTERNAL
    }

    private List<Auth> accounts = Lists.newArrayList();
    private AuthType authType;
    private String loginPageUrl;
    private String loginApi;
    private String loginSuccessUrl;
    private String logoutApi;
    private String logoutSuccessUrl;
    private List<String> authPatterns = Lists.newArrayList();
    private List<String> ignorePatterns = Lists.newArrayList();
    private List<String> httpRequestPatterns = Lists.newArrayList();
    private String externalAuthUrl;

    public List<Auth> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Auth> accounts) {
        this.accounts = accounts;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public String getLoginPageUrl() {
        return loginPageUrl;
    }

    public void setLoginPageUrl(String loginPageUrl) {
        this.loginPageUrl = loginPageUrl;
    }

    public String getLoginApi() {
        return loginApi;
    }

    public void setLoginApi(String loginApi) {
        this.loginApi = loginApi;
    }

    public String getLoginSuccessUrl() {
        return loginSuccessUrl;
    }

    public void setLoginSuccessUrl(String loginSuccessUrl) {
        this.loginSuccessUrl = loginSuccessUrl;
    }

    public String getLogoutApi() {
        return logoutApi;
    }

    public void setLogoutApi(String logoutApi) {
        this.logoutApi = logoutApi;
    }

    public String getLogoutSuccessUrl() {
        return logoutSuccessUrl;
    }

    public void setLogoutSuccessUrl(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }

    public List<String> getAuthPatterns() {
        return authPatterns;
    }

    public void setAuthPatterns(List<String> authPatterns) {
        this.authPatterns = authPatterns;
    }

    public List<String> getIgnorePatterns() {
        return ignorePatterns;
    }

    public void setIgnorePatterns(List<String> ignorePatterns) {
        this.ignorePatterns = ignorePatterns;
    }

    public List<String> getHttpRequestPatterns() {
        return httpRequestPatterns;
    }

    public void setHttpRequestPatterns(List<String> httpRequestPatterns) {
        this.httpRequestPatterns = httpRequestPatterns;
    }

    public String getExternalAuthUrl() {
        return externalAuthUrl;
    }

    public void setExternalAuthUrl(String externalAuthUrl) {
        this.externalAuthUrl = externalAuthUrl;
    }
}