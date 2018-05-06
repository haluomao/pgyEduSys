package com.pgy.util.bce.bos;

import com.baidubce.services.bos.BosClient;

/**
 * The BOS client provider.
 *
 * @author Felix
 */
public interface BosClientProvider {

    /**
     * Create BOS client.
     *
     * @return The BOS client.
     */
    BosClient createBosClient();

}
