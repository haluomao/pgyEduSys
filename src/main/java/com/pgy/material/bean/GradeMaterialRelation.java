package com.pgy.material.bean;

import com.google.common.base.Objects;
import com.pgy.common.bean.Status;

/**
 * The grade-material relation.
 *
 * @author Felix
 */
public class GradeMaterialRelation {
    private long id;
    private long gradeId;
    private long materialId;
    private Status status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;
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
        final StringBuilder builder = new StringBuilder("GradeMaterialRelation{");
        builder.append("id=");
        builder.append(id);
        builder.append(", gradeId=");
        builder.append(gradeId);
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
        if (!(o instanceof GradeMaterialRelation)) {
            return false;
        }
        GradeMaterialRelation that = (GradeMaterialRelation) o;
        return id == that.id
                && gradeId == that.gradeId
                && materialId == that.materialId
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, gradeId, materialId, status);
    }
}
