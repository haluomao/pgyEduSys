package com.pgy.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The executor utils.
 *
 * @author Felix
 */
public class ExecutorUtils {
    private static final Log log = LogFactory.getLog(ExecutorUtils.class);

    private static final long EXECUTOR_TERMINATION_TIMEOUT = 60;

    public static void shutdownAndWait(ExecutorService executor, String name) {
        log.info("Shutting down " + name);
        executor.shutdown();
        awaitTermination(executor, name);
    }

    public static void shutdownNowAndWait(ExecutorService executor, String name) {
        log.info("Shutting down " + name + " now.");
        executor.shutdownNow();
        awaitTermination(executor, name);
    }

    public static void shutdownImmediately(ExecutorService executor, String name) {
        log.info("Shutting down " + name + " immediately.");
        executor.shutdownNow();
    }

    private static void awaitTermination(ExecutorService executor, String name) {
        try {
            while (!executor.awaitTermination(EXECUTOR_TERMINATION_TIMEOUT, TimeUnit.SECONDS)) {
                log.info("Waiting for all tasks complete execution in " + name);
            }
            log.info(name + " is shut down.");
        } catch (InterruptedException e) {
            log.error("Shutting down " + name + " failed.", e);
            Thread.currentThread().interrupt();
        }
    }

}
