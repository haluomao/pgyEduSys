package com.pgy.category.bean;

import com.google.common.base.Objects;
import com.pgy.common.bean.Status;

/**
 * The category.
 *
 * @author Felix
 */
public class Category {
    private long id;
    private String name;
    private String description;
    private long price;
    private Status status;

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
        final StringBuilder builder = new StringBuilder("Category{");
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
        if (!(o instanceof Category)) {
            return false;
        }
        Category category = (Category) o;
        return id == category.id
                && price == category.price
                && Objects.equal(name, category.name)
                && Objects.equal(description, category.description)
                && status == category.status;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, price, status);
    }
}
