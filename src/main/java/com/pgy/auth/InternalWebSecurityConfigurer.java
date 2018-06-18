package com.pgy.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.baidubce.util.JsonUtils;
import com.pgy.auth.bean.CustomUser;
import com.pgy.common.CollectionHelper;
import com.pgy.rest.RestResponseFactory;

/**
 * Web security configurer for internal authentication.
 *
 * @author Felix
 */
@ConditionalOnProperty(prefix = "auth", name = "authType", havingValue = "INTERNAL")
@EnableWebSecurity
public class InternalWebSecurityConfigurer extends WebSecurityConfigurer {

    private final UserDetailsService userDetailsService;

    @Autowired
    public InternalWebSecurityConfigurer(AuthConfig authConfig,
            UserDetailsService userDetailsService) {
        super(authConfig);
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder authBuilder) {
        authBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        configureAuth(http);
        configureFormLogin(http);
        configureLogout(http);
        configureException(http);
        configureCsrf(http);
    }

    @Bean
    protected AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, new PlainPasswordEncoder());
    }

    @Override
    protected AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {
                authAjaxResponse(response, JsonUtils.toJsonString(
                        RestResponseFactory.newSuccessfulResponse(getUserInfo(authentication.getName()))));
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

    private AuthConfig.Auth getUserInfo(String username) {
        CustomUser user = (CustomUser) userDetailsService.loadUserByUsername(username);
        AuthConfig.Auth auth = new AuthConfig.Auth();
        auth.setId(user.getUserId());
        auth.setRole(user.getRole().name());
        auth.setUsername(username);
        return auth;
    }
}