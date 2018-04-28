package com.pgy.rest;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * The rest page.
 *
 * @author Feilx
 */
public class RestPage<T> {
    public static class Builder<T> {
        private int totalCount;
        private int pageNo;
        private int pageSize;
        private String orderBy;
        private OrderType order;
        private List<T> result;

        public Builder<T> withBasePagedRequest(BasePagedRequest request) {
            pageNo = request.getPageNo();
            pageSize = request.getPageSize();
            orderBy = request.getOrderBy();
            order = request.getOrder();
            return this;
        }

        public Builder<T> withTotalCount(int val) {
            totalCount = val;
            return this;
        }

        public Builder<T> withPageNo(int val) {
            pageNo = val;
            return this;
        }

        public Builder<T> withPageSize(int val) {
            pageSize = val;
            return this;
        }

        public Builder<T> withOrderBy(String val) {
            orderBy = val;
            return this;
        }

        public Builder<T> withOrder(OrderType val) {
            order = val;
            return this;
        }

        public Builder<T> withResult(List<T> val) {
            result = val;
            return this;
        }

        public RestPage<T> build() {
            Preconditions.checkNotNull(orderBy);
            Preconditions.checkNotNull(order);
            Preconditions.checkNotNull(result);

            RestPage<T> restPage = new RestPage<T>();
            restPage.totalCount = totalCount;
            restPage.pageNo = pageNo;
            restPage.pageSize = pageSize;
            restPage.orderBy = orderBy;
            restPage.order = order;
            restPage.result = result;
            return restPage;
        }
    }

    private int totalCount;
    private int pageNo;
    private int pageSize;
    private String orderBy;
    private OrderType order;
    private List<T> result;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public OrderType getOrder() {
        return order;
    }

    public void setOrder(OrderType order) {
        this.order = order;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{RestPage,totalCount=");
        builder.append(totalCount);
        builder.append(",pageNo=");
        builder.append(pageNo);
        builder.append(",pageSize=");
        builder.append(pageSize);
        builder.append(",orderBy=");
        builder.append(orderBy);
        builder.append(",order=");
        builder.append(order);
        builder.append(",result=");
        builder.append(result);
        builder.append("}");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RestPage)) {
            return false;
        }
        RestPage<?> restPage = (RestPage<?>) o;
        return totalCount == restPage.totalCount
                && pageNo == restPage.pageNo
                && pageSize == restPage.pageSize
                && Objects.equal(orderBy, restPage.orderBy)
                && order == restPage.order
                && Objects.equal(result, restPage.result);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(totalCount, pageNo, pageSize, orderBy, order, result);
    }
}
