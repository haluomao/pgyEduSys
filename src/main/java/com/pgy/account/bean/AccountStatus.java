package com.pgy.account.bean;

/**
 * Account status.
 *
 * @author Felix
 */
public enum AccountStatus {
    DELETE(-1),
    ENABLED(0),
    DISABLED(1),
    EXPIRED(2);

    int value;

    AccountStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static AccountStatus parse(int value) {
        for (AccountStatus status : AccountStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }
}
