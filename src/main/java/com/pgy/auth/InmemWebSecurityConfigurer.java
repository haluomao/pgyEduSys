package com.pgy.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.pgy.common.CollectionHelper;

/**
 * Web security configurer for in-mem authentication.
 *
 * @author Felix
 */
@ConditionalOnProperty(prefix = "auth", name = "authType", havingValue = "INMEM")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class InmemWebSecurityConfigurer extends WebSecurityConfigurer {

    @Autowired
    public InmemWebSecurityConfigurer(AuthConfig authConfig) {
        super(authConfig);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception {
        for (AuthConfig.Auth auth : getAuthConfig().getAccounts()) {
            authBuilder.inMemoryAuthentication()
                    .withUser(auth.getUsername())
                    .password(auth.getPassword())
                    .roles(auth.getRole());
        }
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        configureAuth(http);
        configureFormLogin(http);
        configureLogout(http);
        configureException(http);
        configureCsrf(http);
    }

    @Override
    protected AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
                authAjaxResponse(response, SUCCESS_AJAX_RESPONSE);
            }
        };
    }

    @Override
    protected AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                    AuthenticationException exception) throws IOException, ServletException {
                authenticatedFailed(request, response, exception);
            }
        };
    }

    @Override
    protected LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
                authAjaxResponse(response, SUCCESS_AJAX_RESPONSE);
            }
        };
    }

    @Override
    protected AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                    AuthenticationException authException) throws IOException, ServletException {
                authenticatedFailed(request, response, authException);
            }
        };
    }

    protected void authenticatedFailed(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        String loginPageUrl = getAuthConfig().getLoginPageUrl();
        for (RequestMatcher requestMatcher : CollectionHelper.getNonNullList(getHttpRequestMatchers())) {
            if (requestMatcher.matches(request)) {
                authHttpRedirectResponse(response, loginPageUrl);
                return;
            }
        }
        authAjaxRedirectResponse(response, loginPageUrl);
    }
}