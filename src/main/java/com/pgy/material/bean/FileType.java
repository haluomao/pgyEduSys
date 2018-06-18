package com.pgy.material.bean;

import com.google.common.collect.Sets;

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
    EXCEL(6),
    HTML(7),
    ZIP(8);

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

    public static FileType parseStr(String value) {
        if (Sets.newHashSet("application/zip", "application/x-zip-compressed",
                "application/octet-stream")
                .contains(value.toLowerCase())) {
            return ZIP;
        }
        return null;
    }
}
