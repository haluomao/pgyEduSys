package com.pgy.controller.bean;

import com.google.common.base.Objects;

/**
 * The id request.
 *
 * @author Felix
 */
public class IdRequest {
    public static final class Builder {
        private long id;

        private Builder() {
        }

        public static Builder anIdRequest() {
            return new Builder();
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public IdRequest build() {
            IdRequest idRequest = new IdRequest();
            idRequest.setId(id);
            return idRequest;
        }
    }

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("IdRequest{");
        builder.append("id=");
        builder.append(id);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IdRequest)) {
            return false;
        }
        IdRequest idRequest = (IdRequest) o;
        return id == idRequest.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
