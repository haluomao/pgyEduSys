package com.pgy.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Boot app.
 *
 * @author Felix
 */
@SpringBootApplication(scanBasePackages = { "com.pgy" })
public class WebApplication extends BaseServer {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
