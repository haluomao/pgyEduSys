package com.pgy.auth.bean;

/**
 * Role.
 *
 * @author Felix
 */
public enum Role {
    SUPER_ADMIN(1),
    ADMIN(2),
    TEACHER(3),
    USER(4),
    GUEST(5);

    int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Role parse(int value) {
        for (Role item : Role.values()) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }

    public boolean isAdmin() {
        return this == SUPER_ADMIN || this == ADMIN;
    }
}
