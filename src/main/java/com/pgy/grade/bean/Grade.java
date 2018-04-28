package com.pgy.grade.bean;

import com.google.common.base.Objects;
import com.pgy.common.bean.Status;

/**
 * The grade.
 *
 * @author Felix
 */
public class Grade {
    private long id;
    private String gradeName;
    private String description;
    private Status status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Grade{");
        builder.append("id=");
        builder.append(id);
        builder.append(", gradeName=");
        builder.append(gradeName);
        builder.append(", description=");
        builder.append(description);
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
        if (!(o instanceof Grade)) {
            return false;
        }
        Grade grade = (Grade) o;
        return id == grade.id
                && Objects.equal(gradeName, grade.gradeName)
                && Objects.equal(description, grade.description)
                && status == grade.status;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, gradeName, description, status);
    }
}
