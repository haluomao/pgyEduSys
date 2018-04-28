package com.pgy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The common config for cloud adx.
 *
 * @author Felix
 */
@Component
@ConfigurationProperties(prefix = "jdbc")
public class DbConfig {

    private int dbReadBatchSize = 5000;
    private int dbWriteBatchSize = 1000;

    private String dbUrl = "jdbc:mysql://localhost/pgy"
            + "?useUnicode=true&characterEncoding=UTF8&autoReconnect=true&zeroDateTimeBehavior=convertToNull";
    private String dbUser = "tester";
    private String dbPassword = "123456";

    public int getDbReadBatchSize() {
        return dbReadBatchSize;
    }

    public void setDbReadBatchSize(int dbReadBatchSize) {
        this.dbReadBatchSize = dbReadBatchSize;
    }

    public int getDbWriteBatchSize() {
        return dbWriteBatchSize;
    }

    public void setDbWriteBatchSize(int dbWriteBatchSize) {
        this.dbWriteBatchSize = dbWriteBatchSize;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
}
