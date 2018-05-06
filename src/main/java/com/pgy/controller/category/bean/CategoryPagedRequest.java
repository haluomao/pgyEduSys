package com.pgy.controller.category.bean;

import com.google.common.base.Objects;
import com.pgy.rest.BasePagedRequest;
import com.pgy.rest.OrderType;

/**
 * Category paged request.
 *
 * @author Felix
 */
public class CategoryPagedRequest extends BasePagedRequest {

    public static final class Builder {
        protected int pageNo = 1;
        protected int pageSize = 10;
        protected String orderBy;
        protected OrderType order = OrderType.DESC;
        private String name;

        private Builder() {
        }

        public static Builder aCategoryPagedRequest() {
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

        public CategoryPagedRequest build() {
            CategoryPagedRequest categoryPagedRequest = new CategoryPagedRequest();
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

    public CategoryPagedRequest() {
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
        final StringBuilder builder = new StringBuilder("CategoryPagedRequest{");
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
        if (!(o instanceof CategoryPagedRequest)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        CategoryPagedRequest that = (CategoryPagedRequest) o;
        return Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), name);
    }
}
