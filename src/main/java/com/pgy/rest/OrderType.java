package com.pgy.rest;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The order by type.
 *
 * @author Felix
 */
public enum OrderType {
    ASC("asc"),
    DESC("desc");

    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public boolean isReverse() {
        return this == DESC;
    }

    public static OrderType parse(String value) {
        for (OrderType orderType : OrderType.values()) {
            if (orderType.getValue().equalsIgnoreCase(value)) {
                return orderType;
            }
        }
        return null;
    }
}
