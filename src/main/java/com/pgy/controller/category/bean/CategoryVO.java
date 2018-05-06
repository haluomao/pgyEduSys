package com.pgy.controller.category.bean;

import com.google.common.base.Objects;
import com.pgy.category.bean.Category;
import com.pgy.common.bean.Status;

/**
 * The category request.
 *
 * @author Felix
 */
public class CategoryVO {

    public static final class Builder {
        private long id;
        private String name;
        private String description;
        private long price;
        private Status status;

        private Builder() {
        }

        public static Builder aCategoryRequest() {
            return new Builder();
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPrice(long price) {
            this.price = price;
            return this;
        }

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder withCategory(Category category) {
            this.id = category.getId();
            this.name = category.getName();
            this.description = category.getDescription();
            this.price = category.getPrice();
            this.status = category.getStatus();
            return this;
        }

        public CategoryVO build() {
            CategoryVO categoryRequest = new CategoryVO();
            categoryRequest.setId(id);
            categoryRequest.setName(name);
            categoryRequest.setDescription(description);
            categoryRequest.setPrice(price);
            categoryRequest.setStatus(status);
            return categoryRequest;
        }
    }

    private long id;
    private String name;
    private String description;
    private long price;
    private Status status;

    public Category buildCategory() {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setStatus(status);
        category.setPrice(price);
        category.setId(id);
        return category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("CategoryVO{");
        builder.append("id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", description=");
        builder.append(description);
        builder.append(", price=");
        builder.append(price);
        builder.append(", status=");
        builder.append(status);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryVO)) {
            return false;
        }
        CategoryVO that = (CategoryVO) o;
        return id == that.id
                && price == that.price
                && Objects.equal(name, that.name)
                && Objects.equal(description, that.description)
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, price, status);
    }

}
