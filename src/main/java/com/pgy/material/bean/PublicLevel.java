package com.pgy.material.bean;

/**
 * The public Level.
 *
 * @author Felix
 */
public enum PublicLevel {
    UNKNOWN(-1),
    PRIVATE(1),
    PUBLIC(2),
    HALF_PUBLIC(3),
    ONLINE_PUBLIC(4);

    int value;

    PublicLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PublicLevel parse(int value) {
        for (PublicLevel status : PublicLevel.values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }
}
