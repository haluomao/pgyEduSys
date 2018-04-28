package com.pgy.rest;

import com.google.common.base.Objects;

/**
 * The global message.
 *
 * @author Felix
 */
public class GlobalMessage {
    private String global;

    public GlobalMessage(String global) {
        this.global = global;
    }

    public String getGlobal() {
        return global;
    }

    public void setGlobal(String global) {
        this.global = global;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GlobalMessage)) {
            return false;
        }
        GlobalMessage that = (GlobalMessage) o;
        return Objects.equal(global, that.global);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(global);
    }
}
