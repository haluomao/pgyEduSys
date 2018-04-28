package com.pgy.filter;

import java.util.UUID;

import org.apache.log4j.NDC;

/**
 * Helper methods for NDC.
 *
 * NDC（Nested Diagnostic Context）和MDC（Mapped Diagnostic Context）是log4j种非常有用的两个类，
 * 它们用于存储应用程序的上下文信息（context infomation），从而便于在log中使用这些上下文信息。
 *
 * @author Felix
 */
public class LogHelper {

    public static void set() {
        NDC.remove();
        NDC.push(getUUID());
    }

    public static void set(String logId) {
        NDC.push(logId);
    }

    public static String get() {
        return NDC.peek();
    }

    public static void remove() {
        NDC.remove();
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
