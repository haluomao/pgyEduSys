package com.pgy.util.bce.bos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pgy.config.UploadConfig;
import com.pgy.util.bce.bos.bean.BosConfig;

/**
 * Bos factory.
 *
 * @author Felix
 */
@Component
public class BosFactory {

    private final BosUtil bosUtil;

    @Autowired
    public BosFactory(UploadConfig bosConfigs) {
        BosConfig config = bosConfigs.getBos();
        if (config != null) {
            bosUtil = new BosUtil(new BosClientProviderImpl(config), config);
        } else {
            bosUtil = null;
        }
    }

    public BosUtil getBosUtil() {
        return bosUtil;
    }
}
