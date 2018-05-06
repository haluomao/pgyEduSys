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
    public class LocalConfig {
        public String dir;
        public String prefix;
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
