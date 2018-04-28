package com.pgy.common.bean;

/**
 * Status.
 *
 * @author Felix
 */
public enum Status {
    DELETE(-1),
    ENABLED(0),
    DISABLED(1);

    int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Status parse(int value) {
        for (Status status : Status.values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }
}
