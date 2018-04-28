package com.pgy.common.bean;

/**
 * File type.
 *
 * @author Felix
 */
public enum FileType {
    UNKNOWN(-1),
    TXT(1),
    PPT(2),
    FLASH(3),
    VIDEO(4),
    DOC(5),
    EXCEL(6);

    int value;

    FileType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FileType parse(int value) {
        for (FileType status : FileType.values()) {
            if (status.value == value) {
                return status;
            }
        }
        return null;
    }
}
