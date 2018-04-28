package com.pgy.account.bean;

/**
 * Role.
 *
 * @author Felix
 */
public enum Role {
    ADMIN(1),
    USER(2),
    GUEST(3);

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
}
