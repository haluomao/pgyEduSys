package com.pgy.controller.grade.bean;

import com.google.common.base.Objects;
import com.pgy.rest.BasePagedRequest;
import com.pgy.rest.OrderType;

/**
 * Grade paged request.
 *
 * @author Felix
 */
public class GradePagedRequest extends BasePagedRequest {

    public static final class Builder {
        protected int pageNo = 1;
        protected int pageSize = 10;
        protected String orderBy = ORDER_BY;
        protected OrderType order = OrderType.DESC;
        private String name;

        private Builder() {
        }

        public static Builder aGradePagedRequest() {
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

        public GradePagedRequest build() {
            GradePagedRequest categoryPagedRequest = new GradePagedRequest();
            categoryPagedRequest.setPageNo(pageNo);
            categoryPagedRequest.setPageSize(pageSize);
            categoryPagedRequest.setOrderBy(orderBy);
            categoryPagedRequest.setOrder(order);
            categoryPagedRequest.setName(name);
            return categoryPagedRequest;
        }
    }

    private static final String ORDER_BY = "id";
    private String name;

    public GradePagedRequest() {
        super(ORDER_BY);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("GradePagedRequest{");
        builder.append("name=");
        builder.append(name);
        builder.append(", pageNo=");
        builder.append(pageNo);
        builder.append(", pageSize=");
        builder.append(pageSize);
        builder.append(", orderBy=");
        builder.append(orderBy);
        builder.append(", order=");
        builder.append(order);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GradePagedRequest)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        GradePagedRequest that = (GradePagedRequest) o;
        return Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), name);
    }
}
