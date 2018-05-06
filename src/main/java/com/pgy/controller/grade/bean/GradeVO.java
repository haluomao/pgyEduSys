package com.pgy.controller.grade.bean;

import com.google.common.base.Objects;
import com.pgy.common.bean.Status;
import com.pgy.grade.bean.Grade;

/**
 * The grade request.
 *
 * @author Felix
 */
public class GradeVO {

    public static final class Builder {
        private long id;
        private String name;
        private String description;
        private Status status;

        private Builder() {
        }

        public static Builder aGradeRequest() {
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

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder withGrade(Grade grade) {
            this.id = grade.getId();
            this.name = grade.getGradeName();
            this.description = grade.getDescription();
            this.status = grade.getStatus();
            return this;
        }

        public GradeVO build() {
            GradeVO gradeRequest = new GradeVO();
            gradeRequest.setId(id);
            gradeRequest.setName(name);
            gradeRequest.setDescription(description);
            gradeRequest.setStatus(status);
            return gradeRequest;
        }
    }

    private long id;
    private String name;
    private String description;
    private long price;
    private Status status;

    public Grade buildGrade() {
        Grade grade = new Grade();
        grade.setGradeName(name);
        grade.setDescription(description);
        grade.setStatus(status);
        grade.setId(id);
        return grade;
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
        final StringBuilder builder = new StringBuilder("GradeVO{");
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
        if (!(o instanceof GradeVO)) {
            return false;
        }
        GradeVO gradeVO = (GradeVO) o;
        return id == gradeVO.id
                && price == gradeVO.price
                && Objects.equal(name, gradeVO.name)
                && Objects.equal(description, gradeVO.description)
                && status == gradeVO.status;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, price, status);
    }
}
