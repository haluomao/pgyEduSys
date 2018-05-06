package com.pgy.controller.material.bean;

import com.google.common.base.Objects;
import com.pgy.rest.BasePagedRequest;
import com.pgy.rest.OrderType;

/**
 * The material paged request.
 *
 * @author Felix
 */
public class MaterialPagedRequest extends BasePagedRequest {

    public static final class Builder {
        protected int pageNo = 1;
        protected int pageSize = 10;
        protected String orderBy;
        protected OrderType order = OrderType.DESC;
        private String name;

        private Builder() {
        }

        public static Builder aMaterialPagedRequest() {
            return new Builder();
        }

        public Builder withPageNo(int pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public Builder withPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder withOrderBy(String orderBy) {
            this.orderBy = orderBy;
            return this;
        }

        public Builder withOrder(OrderType order) {
            this.order = order;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public MaterialPagedRequest build() {
            MaterialPagedRequest materialPagedRequest = new MaterialPagedRequest();
            materialPagedRequest.setPageNo(pageNo);
            materialPagedRequest.setPageSize(pageSize);
            materialPagedRequest.setOrderBy(orderBy);
            materialPagedRequest.setOrder(order);
            materialPagedRequest.setName(name);
            return materialPagedRequest;
        }
    }

    private static final String ORDER_BY = "id";
    private String name;

    public MaterialPagedRequest() {
        super(ORDER_BY);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterialPagedRequest)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MaterialPagedRequest that = (MaterialPagedRequest) o;
        return Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("MaterialPagedRequest{");
        builder.append("name=");
        builder.append(name);
        builder.append('}');
        return builder.toString();
    }

}
