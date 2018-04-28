package com.pgy.rest;

import com.google.common.base.Objects;

/**
 * The base paged request.
 *
 * @author Felix
 */
public abstract class BasePagedRequest {

    protected int pageNo = 1;
    protected int pageSize = 10;
    protected String orderBy;
    protected OrderType order = OrderType.DESC;

    public BasePagedRequest(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }

    public int getLimit() {
        return pageSize;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{BasePagedRequest, pageNo=");
        builder.append(pageNo);
        builder.append(", pageSize=");
        builder.append(pageSize);
        builder.append(", orderBy=");
        builder.append(orderBy);
        builder.append(", order=");
        builder.append(order);
        builder.append("}");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BasePagedRequest)) {
            return false;
        }
        BasePagedRequest that = (BasePagedRequest) o;
        return pageNo == that.pageNo
                && pageSize == that.pageSize
                && Objects.equal(orderBy, that.orderBy)
                && order == that.order;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pageNo, pageSize, orderBy, order);
    }
}
