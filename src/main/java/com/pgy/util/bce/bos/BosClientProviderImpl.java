package com.pgy.util.bce.bos;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.pgy.util.bce.bos.bean.BosConfig;

/**
 * The BOS client provider.
 *
 * @author Felix
 */
public class BosClientProviderImpl implements BosClientProvider {

    private final BosConfig bosConfig;

    public BosClientProviderImpl(BosConfig bosConfig) {
        this.bosConfig = bosConfig;
    }

    @Override
    public BosClient createBosClient() {
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(bosConfig.getAk(), bosConfig.getSk()));
        config.setEndpoint(bosConfig.getEndPoint());
        return new BosClient(config);
    }

}
