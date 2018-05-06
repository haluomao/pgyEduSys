package com.pgy.material.bean;

/**
 * The public Level.
 *
 * @author Felix
 */
public enum PublicLevel {
    UNKNOWN(-1),
    PUBLIC(1),
    HALF_PUBLIC(2),
    PRIVATE(3);

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
