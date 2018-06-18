package com.pgy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The schedule config.
 *
 * @author Felix
 */
@Component
@ConfigurationProperties(prefix = "schedule")
public class ScheduleConfig {
    private boolean userCheckEnable = false;
    private String userCheckCron;

    public String getUserCheckCron() {
        return userCheckCron;
    }

    public void setUserCheckCron(String userCheckCron) {
        this.userCheckCron = userCheckCron;
    }

    public boolean isUserCheckEnable() {
        return userCheckEnable;
    }

    public void setUserCheckEnable(boolean userCheckEnable) {
        this.userCheckEnable = userCheckEnable;
    }
}
