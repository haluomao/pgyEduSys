package com.pgy.rest;

import java.util.List;

import com.google.common.base.Objects;

/**
 * The id list request.
 *
 * @author Felix
 */
public class IdListRequest {

    public static final class Builder {
        private List<Long> idList;

        private Builder() {
        }

        public static Builder anIdListRequest() {
            return new Builder();
        }

        public Builder withIdList(List<Long> idList) {
            this.idList = idList;
            return this;
        }

        public IdListRequest build() {
            IdListRequest idListRequest = new IdListRequest();
            idListRequest.setIdList(idList);
            return idListRequest;
        }
    }

    private List<Long> idList;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("IdListRequest{");
        builder.append("idList=");
        builder.append(idList);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IdListRequest)) {
            return false;
        }
        IdListRequest that = (IdListRequest) o;
        return Objects.equal(idList, that.idList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idList);
    }

}
