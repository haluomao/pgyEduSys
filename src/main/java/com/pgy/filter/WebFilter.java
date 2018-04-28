package com.pgy.filter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.pgy.common.LogMessageBuilder;

/**
 * Web request filter.
 *
 * @author Felix
 */
public class WebFilter implements Filter {

    private static final Log log = LogFactory.getLog(WebFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            LogHelper.set();
            chain.doFilter(request, response);
        } finally {
            stopwatch.stop();
            log.info(new LogMessageBuilder()
                    .withMessage("HTTP Request")
                    .withParameter("url", httpServletRequest.getRequestURI() + getQueryString(httpServletRequest))
                    .withParameter("cost", stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms"));
            LogHelper.remove();
        }
    }

    private String getQueryString(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (Strings.isNullOrEmpty(queryString)) {
            return StringUtils.EMPTY;
        }
        return "?" + queryString;
    }

    @Override
    public void destroy() {
    }
}
