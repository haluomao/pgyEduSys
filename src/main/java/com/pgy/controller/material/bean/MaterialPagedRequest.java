package com.pgy.controller.material.bean;

import com.google.common.base.Objects;
import com.pgy.material.bean.FileType;
import com.pgy.material.bean.PublicLevel;
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
        protected String orderBy = ORDER_BY;
        protected OrderType order = OrderType.DESC;
        private String name;
        private long categoryId;
        private long gradeId;
        private FileType fileType;
        private PublicLevel publicLevel;
        private long authorId;

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

        public Builder withCategoryId(long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder withGradeId(long gradeId) {
            this.gradeId = gradeId;
            return this;
        }

        public Builder withFileType(FileType fileType) {
            this.fileType = fileType;
            return this;
        }

        public Builder withPublicLevel(PublicLevel publicLevel) {
            this.publicLevel = publicLevel;
            return this;
        }

        public Builder withAuthorId(long authorId) {
            this.authorId = authorId;
            return this;
        }

        public MaterialPagedRequest build() {
            MaterialPagedRequest materialPagedRequest = new MaterialPagedRequest();
            materialPagedRequest.setPageNo(pageNo);
            materialPagedRequest.setPageSize(pageSize);
            materialPagedRequest.setOrderBy(orderBy);
            materialPagedRequest.setOrder(order);
            materialPagedRequest.setName(name);
            materialPagedRequest.setCategoryId(categoryId);
            materialPagedRequest.setGradeId(gradeId);
            materialPagedRequest.setFileType(fileType);
            materialPagedRequest.setPublicLevel(publicLevel);
            materialPagedRequest.setAuthorId(authorId);
            return materialPagedRequest;
        }
    }

    private static final String ORDER_BY = "id";
    private String name;
    private long categoryId;
    private long gradeId;
    private FileType fileType;
    private PublicLevel publicLevel;
    private long authorId;

    public MaterialPagedRequest() {
        super(ORDER_BY);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public PublicLevel getPublicLevel() {
        return publicLevel;
    }

    public void setPublicLevel(PublicLevel publicLevel) {
        this.publicLevel = publicLevel;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
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
        return categoryId == that.categoryId
                && gradeId == that.gradeId
                && authorId == that.authorId
                && Objects.equal(name, that.name)
                && fileType == that.fileType
                && publicLevel == that.publicLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), name, categoryId, gradeId, fileType, publicLevel, authorId);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("MaterialPagedRequest{");
        builder.append("name=");
        builder.append(name);
        builder.append(", categoryId=");
        builder.append(categoryId);
        builder.append(", gradeId=");
        builder.append(gradeId);
        builder.append(", fileType=");
        builder.append(fileType);
        builder.append(", publicLevel=");
        builder.append(publicLevel);
        builder.append(", authorId=");
        builder.append(authorId);
        builder.append('}');
        return builder.toString();
    }

}
