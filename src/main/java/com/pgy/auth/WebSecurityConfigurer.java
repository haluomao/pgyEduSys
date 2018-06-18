package com.pgy.auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.google.common.base.Function;
import com.pgy.common.CollectionHelper;
import com.pgy.common.JsonConverter;
import com.pgy.filter.WebFilter;
import com.pgy.rest.RestResponseFactory;

/**
 * The web security configurer.
 *
 * @author Felix
 */
public abstract class WebSecurityConfigurer  extends WebSecurityConfigurerAdapter {

    protected static final String SUCCESS_AJAX_RESPONSE = JsonConverter.serialize(
            RestResponseFactory.newSuccessfulEmptyResponse());

    protected AuthConfig authConfig;
    protected List<RequestMatcher> httpRequestMatchers;

    public WebSecurityConfigurer(AuthConfig authConfig) {
        this.authConfig = authConfig;
        if (CollectionUtils.isNotEmpty(authConfig.getHttpRequestPatterns())) {
            httpRequestMatchers = CollectionHelper.transform(authConfig.getHttpRequestPatterns(),
                    new Function<String, RequestMatcher>() {
                        @Override
                        public RequestMatcher apply(String input) {
                            return new AntPathRequestMatcher(input);
                        }
                    });
        }
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        if (CollectionUtils.isNotEmpty(getAuthConfig().getIgnorePatterns())) {
            web.ignoring().antMatchers(getAuthConfig().getIgnorePatterns().toArray(new String[0]));
        }
    }

    @Bean
    public FilterRegistrationBean requestPerformanceFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebFilter());
        filterRegistrationBean.setUrlPatterns(CollectionHelper.getNonNullList(authConfig.getAuthPatterns()));
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    public AuthConfig getAuthConfig() {
        return authConfig;
    }

    public void setAuthConfig(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    public List<RequestMatcher> getHttpRequestMatchers() {
        return httpRequestMatchers;
    }

    public void setHttpRequestMatchers(List<RequestMatcher> httpRequestMatchers) {
        this.httpRequestMatchers = httpRequestMatchers;
    }

    protected void configureAuth(HttpSecurity http) throws Exception {
        if (CollectionUtils.isNotEmpty(getAuthConfig().getAuthPatterns())) {
            http.authorizeRequests()
                    .antMatchers(getAuthConfig().getAuthPatterns().toArray(new String[0]))
                    .authenticated();
        }
    }

    protected void configureFormLogin(HttpSecurity http) throws Exception {
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage(getAuthConfig().getLoginPageUrl())
                .loginProcessingUrl(getAuthConfig().getLoginApi())
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .permitAll();
    }

    protected void configureLogout(HttpSecurity http) throws Exception {
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(getAuthConfig().getLogoutApi()))
                .logoutSuccessUrl(getAuthConfig().getLogoutSuccessUrl())
                .invalidateHttpSession(true)
                .logoutSuccessHandler(logoutSuccessHandler())
                .permitAll();
    }

    protected void configureException(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
    }

    protected void configureCsrf(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    protected void authAjaxResponse(HttpServletResponse response, String responseContent) throws IOException {
        response.setContentType("text/plain");
        response.getWriter().write(responseContent);
        response.getWriter().flush();
    }

    protected void authAjaxRedirectResponse(HttpServletResponse response, String redirectUrl) throws IOException {
        authAjaxResponse(response, JsonConverter.serialize(RestResponseFactory.newGlobalFailedResponse(redirectUrl)));
    }

    protected void authHttpRedirectResponse(HttpServletResponse response, String redirectUrl) throws IOException {
        response.sendRedirect(redirectUrl);
    }

    protected abstract AuthenticationSuccessHandler authenticationSuccessHandler();

    protected abstract AuthenticationFailureHandler authenticationFailureHandler();

    protected abstract LogoutSuccessHandler logoutSuccessHandler();

    protected abstract AuthenticationEntryPoint authenticationEntryPoint();
}
