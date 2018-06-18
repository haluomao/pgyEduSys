package com.pgy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pgy.config.UploadConfig;

/**
 * My conf for resource.
 *
 * Ref: https://www.jianshu.com/p/54e0ca450f88
 *
 * @author Felix
 */
@Configuration
public class MyConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private UploadConfig uploadConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(uploadConfig.getLocal().getResPatterns())
                .addResourceLocations(uploadConfig.getLocal().getLocation());
    }
}
