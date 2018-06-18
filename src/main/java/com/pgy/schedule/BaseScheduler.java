package com.pgy.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;

/**
 * The base scheduler.
 *
 * @author Felix
 */
public abstract class BaseScheduler implements Schedulable {

    private static final Log log = LogFactory.getLog(BaseScheduler.class);

    protected final SafeScheduledExecutorService executor;

    protected volatile boolean disableScheduler = false;

    public BaseScheduler(String name, ExecutorServiceFactory executorServiceFactory) {
        executor = new SafeScheduledExecutorService(name, executorServiceFactory);
    }

    @ManagedAttribute
    public boolean isDisableScheduler() {
        return disableScheduler;
    }

    @ManagedAttribute
    public void setDisableScheduler(boolean disableScheduler) {
        if (this.disableScheduler == disableScheduler) {
            return;
        }

        this.disableScheduler = disableScheduler;

        if (this.disableScheduler) {
            stopCronTask();
        } else {
            startCronTask();
        }
    }

    @Override
    public void setUpScheduledTask() {
        if (!disableScheduler) {
            startCronTask();
        }
    }

    @Override
    public void shutdown() {
        executor.shutdownNowAndWait();
    }

    protected abstract void startCronTask();

    protected void stopCronTask() {
        executor.stop();
    }

    protected void safeExecute(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable t) {
            log.info("Unexpected error in scheduler.", t);
        }
    }

    protected long timingExecute(Runnable runnable) {
        long startTime = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - startTime;
    }

    protected long timingSafeExecute(final Runnable runnable) {
        return timingExecute(new Runnable() {
            @Override
            public void run() {
                safeExecute(runnable);
            }
        });
    }
}