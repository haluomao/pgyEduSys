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
    private boolean userCheckEnable = true;
    private String userCheckCron;
    private boolean fileCleanEnable = false;
    private String fileCleanCron;

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

    public boolean isFileCleanEnable() {
        return fileCleanEnable;
    }

    public void setFileCleanEnable(boolean fileCleanEnable) {
        this.fileCleanEnable = fileCleanEnable;
    }

    public String getFileCleanCron() {
        return fileCleanCron;
    }

    public void setFileCleanCron(String fileCleanCron) {
        this.fileCleanCron = fileCleanCron;
    }
}
