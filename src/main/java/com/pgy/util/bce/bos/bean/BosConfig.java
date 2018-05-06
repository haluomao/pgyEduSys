package com.pgy.util.bce.bos.bean;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

/**
 * Configuration for bos.
 *
 * @author Felix
 */
public class BosConfig {

    private String ak = StringUtils.EMPTY;
    private String sk = StringUtils.EMPTY;
    private String bucket = StringUtils.EMPTY;
    private String uploadDir = StringUtils.EMPTY;
    private String tempDir = StringUtils.EMPTY;
    private List<BosCleanConfig> clean = Lists.newArrayList();
    private boolean enableClean = false;
    private String cleanCron = "0 0 2 * * *";
    private String endPoint = "http://bj.bcebos.com";

    public static class BosCleanConfig implements Serializable {
        private int keepDays;

        public int getKeepDays() {
            return keepDays;
        }

        public void setKeepDays(int keepDays) {
            this.keepDays = keepDays;
        }
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getTempDir() {
        return tempDir;
    }

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    public List<BosCleanConfig> getClean() {
        return clean;
    }

    public void setClean(List<BosCleanConfig> clean) {
        this.clean = clean;
    }

    public boolean isEnableClean() {
        return enableClean;
    }

    public void setEnableClean(boolean enableClean) {
        this.enableClean = enableClean;
    }

    public String getCleanCron() {
        return cleanCron;
    }

    public void setCleanCron(String cleanCron) {
        this.cleanCron = cleanCron;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
}
