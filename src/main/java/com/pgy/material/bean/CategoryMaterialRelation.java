package com.pgy.material.bean;

import com.google.common.base.Objects;
import com.pgy.common.bean.Status;

/**
 * The category-material relation.
 *
 * @author Felix
 */
public class CategoryMaterialRelation {
    private long id;
    private long categoryId;
    private long materialId;
    private Status status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("CategoryMaterialRelation{");
        builder.append("id=");
        builder.append(id);
        builder.append(", categoryId=");
        builder.append(categoryId);
        builder.append(", materialId=");
        builder.append(materialId);
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
        if (!(o instanceof CategoryMaterialRelation)) {
            return false;
        }
        CategoryMaterialRelation that = (CategoryMaterialRelation) o;
        return id == that.id
                && categoryId == that.categoryId
                && materialId == that.materialId
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, categoryId, materialId, status);
    }
}
