package com.pgy.controller.material.bean;

import com.google.common.base.Objects;
import com.pgy.material.bean.FileType;
import com.pgy.material.bean.PublicLevel;
import com.pgy.rest.OrderType;

/**
 * The material criteria.
 *
 * @author Felix
 */
public class MaterialCriteria {

    public static final class Builder {
        private String name;
        private Long authorId;
        private FileType fileType;
        private PublicLevel publicLevel;
        private int pageNo = 1;
        private int pageSize = 10;
        private OrderType order;
        private String orderBy;

        private Builder() {
        }

        public static Builder aMaterialCriteria() {
            return new Builder();
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAuthorId(Long authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder withFileType(FileType fileType) {
            this.fileType = fileType;
            return this;
        }

        public Builder withPageNo(int pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public Builder withPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder withOrder(OrderType order) {
            this.order = order;
            return this;
        }

        public Builder withOrderBy(String orderBy) {
            this.orderBy = orderBy;
            return this;
        }

        public Builder withPublicLevely(PublicLevel publicLevel) {
            this.publicLevel = publicLevel;
            return this;
        }

        public MaterialCriteria build() {
            MaterialCriteria materialCriteria = new MaterialCriteria();
            materialCriteria.setName(name);
            materialCriteria.setAuthorId(authorId);
            materialCriteria.setFileType(fileType);
            materialCriteria.setPublicLevel(publicLevel);
            materialCriteria.setPageNo(pageNo);
            materialCriteria.setPageSize(pageSize);
            materialCriteria.setOrder(order);
            materialCriteria.setOrderBy(orderBy);
            return materialCriteria;
        }
    }

    private String name;
    private Long authorId;
    private FileType fileType;
    private PublicLevel publicLevel;
    private int pageNo;
    private int pageSize;
    private OrderType order;
    private String orderBy;

    public int getLimit() {
        return pageSize;
    }

    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
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

    public OrderType getOrder() {
        return order;
    }

    public void setOrder(OrderType order) {
        this.order = order;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public PublicLevel getPublicLevel() {
        return publicLevel;
    }

    public void setPublicLevel(PublicLevel publicLevel) {
        this.publicLevel = publicLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterialCriteria)) {
            return false;
        }
        MaterialCriteria that = (MaterialCriteria) o;
        return pageNo == that.pageNo
                && pageSize == that.pageSize
                && Objects.equal(name, that.name)
                && Objects.equal(authorId, that.authorId)
                && fileType == that.fileType
                && publicLevel == that.publicLevel
                && order == that.order
                && Objects.equal(orderBy, that.orderBy);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, authorId, fileType, publicLevel, pageNo, pageSize, order, orderBy);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("MaterialCriteria{");
        builder.append("name=");
        builder.append(name);
        builder.append(", authorId=");
        builder.append(authorId);
        builder.append(", fileType=");
        builder.append(fileType);
        builder.append(", publicLevel=");
        builder.append(publicLevel);
        builder.append(", pageNo=");
        builder.append(pageNo);
        builder.append(", pageSize=");
        builder.append(pageSize);
        builder.append(", order=");
        builder.append(order);
        builder.append(", orderBy=");
        builder.append(orderBy);
        builder.append('}');
        return builder.toString();
    }
}
