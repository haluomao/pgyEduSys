
package com.pgy.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Pgy transaction helper.
 *
 * @author Felix
 */
@Component
public class PgyTransactionHelper extends BaseTransactionHelper {

    @Autowired
    public PgyTransactionHelper(PlatformTransactionManager transactionManagerConfig) {
        super(transactionManagerConfig);
    }
}