package com.pgy.material.bean;

/**
 * Material status.
 *
 * @author Felix
 */
public enum MaterialStatus {
    DELETE(-1),
    ENABLED(0),
    DISABLED(1),
    UNAUDITED(2);

    int value;

    MaterialStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MaterialStatus parse(int value) {
        for (MaterialStatus status : MaterialStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }
}
