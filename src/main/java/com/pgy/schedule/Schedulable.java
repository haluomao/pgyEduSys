package com.pgy.schedule;

/**
 * Schedule interface.
 *
 * @author Felix
 */
public interface Schedulable {
    /**
     * Start up the scheduled task on server started.
     */
    void setUpScheduledTask();

    /**
     * Shutdown the task on server stopped.
     */
    void shutdown();
}
