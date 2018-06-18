package com.pgy.web;

import javax.servlet.MultipartConfigElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * The base server.
 *
 * @author Felix
 */
public class BaseServer extends SpringBootServletInitializer {

    private static final Log log = LogFactory.getLog(BaseServer.class);

    @Bean
    public ApplicationListener<ContextRefreshedEvent> provideRefreshedEventListener() {
        return new ApplicationListener<ContextRefreshedEvent>() {
            @Override
            public void onApplicationEvent(ContextRefreshedEvent event) {
                // The jmx tool--jolokia fire a ContextRefreshedEvent.
                // Events fired from child contexts are propagated to the parent context.
                if (event.getApplicationContext().getParent() == null) {
                    afterStart(event.getApplicationContext());
                }
            }
        };
    }

    @Bean
    public ApplicationListener<ContextClosedEvent> provideClosedEventListener() {
        return new ApplicationListener<ContextClosedEvent>() {
            @Override
            public void onApplicationEvent(ContextClosedEvent event) {
                // The jmx tool--jolokia fire a ContextClosedEvent.
                // Events fired from child contexts are propagated to the parent context.
                if (event.getApplicationContext().getParent() == null) {
                    beforeStop(event.getApplicationContext());
                }
            }
        };
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("100MB");
        factory.setMaxRequestSize("100MB");
        return factory.createMultipartConfig();
    }

    /**
     * Triggered after server started.
     *
     * @param applicationContext The spring context.
     */
    protected void afterStart(ApplicationContext applicationContext) {
    }

    /**
     * Triggered before server stop.
     *
     * @param applicationContext The spring context.
     */
    protected void beforeStop(ApplicationContext applicationContext) {
    }
}
