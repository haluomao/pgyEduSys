package com.pgy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.pgy.util.bce.bos.bean.BosConfig;

/**
 * The upload config.
 *
 * @author Felix
 */
@Component
@ConfigurationProperties(prefix = "upload")
public class UploadConfig {
    public static class LocalConfig {
        private String dir;
        private String prefix;
        private String resPatterns;
        private String location;
        private String trashDir;

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getResPatterns() {
            return resPatterns;
        }

        public void setResPatterns(String resPatterns) {
            this.resPatterns = resPatterns;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getTrashDir() {
            return trashDir;
        }

        public void setTrashDir(String trashDir) {
            this.trashDir = trashDir;
        }
    }

    public enum UploadType {
        LOCAL(1),
        BOS(2);

        int value;

        UploadType(int value) {
            this.value = value;
        }
    }

    private UploadType type;
    private LocalConfig local;
    private BosConfig bos;

    public UploadType getType() {
        return type;
    }

    public void setType(UploadType type) {
        this.type = type;
    }

    public LocalConfig getLocal() {
        return local;
    }

    public void setLocal(LocalConfig local) {
        this.local = local;
    }

    public BosConfig getBos() {
        return bos;
    }

    public void setBos(BosConfig bos) {
        this.bos = bos;
    }
}
