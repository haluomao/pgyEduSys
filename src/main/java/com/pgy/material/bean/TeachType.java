package com.pgy.material.bean;

/**
 * Material type.
 *
 * @author Felix
 */
public enum TeachType {
    UNKNOWN(-1),
    COURSEWARE(1),
    CLASSIC(2),
    EXERCISE(3);

    int value;

    TeachType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TeachType parse(int value) {
        for (TeachType status : TeachType.values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }
}
