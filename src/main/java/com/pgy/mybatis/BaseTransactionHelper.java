package com.pgy.mybatis;

import java.util.concurrent.Callable;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * The base transaction helper.
 *
 * @author Felix
 */
public abstract class BaseTransactionHelper {

    private final TransactionTemplate transactionTemplate;

    public BaseTransactionHelper(PlatformTransactionManager platformTransactionManager) {
        // Isolation is REPEAT_READ for InnoDB.
        // https://dev.mysql.com/doc/refman/5.6/en/innodb-transaction-isolation-levels.html
        transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    public void runInTransaction(Runnable runnable) {
        doRunInTransaction(transactionTemplate, runnable);
    }

    public <T> T callInTransaction(final Callable<T> callable) {
        return doCallInTransaction(transactionTemplate, callable);
    }

    private void doRunInTransaction(TransactionTemplate transactionTemplate,
            final Runnable runnable) {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                runnable.run();
                return null;
            }
        });
    }

    private <T> T doCallInTransaction(TransactionTemplate transactionTemplate,
            final Callable<T> callable) {
        return transactionTemplate.execute(new TransactionCallback<T>() {
            @Override
            public T doInTransaction(TransactionStatus status) {
                try {
                    return callable.call();
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
