package com.pgy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.pgy.schedule.UserCheckScheduler;

/**
 * Boot app.
 *
 * @author Felix
 */
@SpringBootApplication(scanBasePackages = {"com.pgy"})
public class PgyWebApplication extends BaseServer implements CommandLineRunner {

    @Autowired
    private UserCheckScheduler userCheckScheduler;

    @Override
    public void run(String... args) throws Exception {
        userCheckScheduler.doUserCheck();
    }

    @Override
    protected void afterStart(ApplicationContext applicationContext) {
        applicationContext.getBean(UserCheckScheduler.class).setUpScheduledTask();
    }

    @Override
    protected void beforeStop(ApplicationContext applicationContext) {
        applicationContext.getBean(UserCheckScheduler.class).shutdown();
    }

    public static void main(String[] args) {
        SpringApplication.run(PgyWebApplication.class, args);
    }

}
