package com.pgy.schedule;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.pgy.common.ExecutorUtils;
import com.pgy.common.LogMessageBuilder;
import com.pgy.common.TimeUtils;

/**
 * A safe scheduled executor service.
 *
 * @author Felix
 */
public class SafeScheduledExecutorService {

    private static final Log log = LogFactory.getLog(SafeScheduledExecutorService.class);

    private final ScheduledExecutorService executor;

    private final TaskScheduler taskScheduler;

    private final String name;

    private final List<ScheduledFuture<?>> futureList = Lists.newArrayList();

    public SafeScheduledExecutorService(String name, ExecutorServiceFactory executorServiceFactory) {
        this.name = name;
        this.executor = executorServiceFactory.newSingleThreadScheduledExecutor(name);
        this.taskScheduler = new ConcurrentTaskScheduler(executor);
    }

    public void scheduleAtFixedRate(Runnable command, long periodInSecond) {
        Preconditions.checkNotNull(command);
        Preconditions.checkArgument(periodInSecond > 0);

        synchronized (futureList) {
            futureList.add(taskScheduler.scheduleAtFixedRate(new SafeTask(command),
                    periodInSecond * TimeUtils.MILLISECONDS_PER_SECOND));
        }
    }

    public void scheduleWithFixedDelay(Runnable command, long delayInSecond) {
        Preconditions.checkNotNull(command);
        Preconditions.checkArgument(delayInSecond > 0);

        // Do not need save this task in the future list.
        executor.schedule(new SafeTask(command), delayInSecond, TimeUnit.SECONDS);
    }

    public void scheduleWithFixedDelay(Runnable command, long initialDelay, long delay,
            TimeUnit timeUnit) {
        Preconditions.checkNotNull(command);
        Preconditions.checkArgument(initialDelay > 0);
        Preconditions.checkArgument(delay > 0);
        Preconditions.checkNotNull(timeUnit);

        // Do not need save this task in the future list.
        executor.scheduleWithFixedDelay(new SafeTask(command), initialDelay, delay, timeUnit);
    }

    public void schedule(Runnable command, Trigger trigger) {
        Preconditions.checkNotNull(command);
        Preconditions.checkNotNull(trigger);

        synchronized (futureList) {
            futureList.add(taskScheduler.schedule(new SafeTask(command), trigger));
        }
    }

    public void execute(Runnable command) {
        Preconditions.checkNotNull(command);

        executor.execute(new SafeTask(command));
    }

    /**
     * Stop all scheduled jobs.
     */
    public void stop() {
        log.info("Stop all jobs.");
        synchronized (futureList) {
            for (ScheduledFuture<?> future : futureList) {
                future.cancel(false);
            }
            futureList.clear();
        }
    }

    public void shutdownAndWait() {
        ExecutorUtils.shutdownAndWait(executor, name);
    }

    public void shutdownNowAndWait() {
        ExecutorUtils.shutdownNowAndWait(executor, name);
    }

    public void shutdownImmediately() {
        ExecutorUtils.shutdownImmediately(executor, name);
    }

    private class SafeTask implements Runnable {

        private final Runnable task;

        public SafeTask(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            log.info(new LogMessageBuilder()
                    .withMessage("Cron job is started.")
                    .withParameter("name", name));

            long startTime = System.currentTimeMillis();
            try {
                task.run();
            } catch (Throwable t) {
                log.error(new LogMessageBuilder()
                        .withMessage("Cron job is failed")
                        .withParameter("name", name), t);
            }

            log.info(new LogMessageBuilder()
                    .withMessage("Cron job is finished.")
                    .withParameter("name", name)
                    .withParameter("runningTime", System.currentTimeMillis() - startTime));
        }

    }
}
