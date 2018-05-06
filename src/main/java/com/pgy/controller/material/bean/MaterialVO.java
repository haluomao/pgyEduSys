package com.pgy.controller.material.bean;

import com.google.common.base.Objects;
import com.pgy.material.bean.FileType;
import com.pgy.material.bean.Material;
import com.pgy.material.bean.MaterialStatus;

/**
 * The material VO.
 *
 * @author Felix
 */
public class MaterialVO {

    public static final class Builder {
        private long id;
        private long authorId;
        private long uploaderId;
        private String name;
        private String description;
        private FileType fileType;
        private String icon;
        private String url;
        private MaterialStatus status;

        private Builder() {
        }

        public static Builder aMaterialVO() {
            return new Builder();
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withAuthorId(long authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder withUploaderId(long uploaderId) {
            this.uploaderId = uploaderId;
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

        public Builder withFileType(FileType fileType) {
            this.fileType = fileType;
            return this;
        }

        public Builder withIcon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withStatus(MaterialStatus status) {
            this.status = status;
            return this;
        }

        public Builder withMaterial(Material material) {
            this.id = material.getId();
            this.authorId = material.getAuthorId();
            this.uploaderId = material.getUploaderId();
            this.name = material.getName();
            this.description = material.getDescription();
            this.fileType = material.getFileType();
            this.icon = material.getIcon();
            this.url = material.getUrl();
            this.status = material.getStatus();
            return this;
        }

        public MaterialVO build() {
            MaterialVO materialVO = new MaterialVO();
            materialVO.setId(id);
            materialVO.setAuthorId(authorId);
            materialVO.setUploaderId(uploaderId);
            materialVO.setName(name);
            materialVO.setDescription(description);
            materialVO.setFileType(fileType);
            materialVO.setIcon(icon);
            materialVO.setUrl(url);
            materialVO.setStatus(status);
            return materialVO;
        }
    }

    private long id;
    private long authorId;
    private long uploaderId;
    private String name;
    private String description;
    private FileType fileType;
    private String icon;
    private String url;
    private MaterialStatus status;

    public Material buildMaterial() {
        Material material = new Material();
        material.setId(id);
        material.setAuthorId(authorId);
        material.setUploaderId(uploaderId);
        material.setIcon(icon);
        material.setUrl(url);
        material.setFileType(fileType);
        material.setName(name);
        material.setDescription(description);
        material.setStatus(status);
        return material;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(long uploaderId) {
        this.uploaderId = uploaderId;
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

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MaterialStatus getStatus() {
        return status;
    }

    public void setStatus(MaterialStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterialVO)) {
            return false;
        }
        MaterialVO that = (MaterialVO) o;
        return id == that.id
                && authorId == that.authorId
                && uploaderId == that.uploaderId
                && Objects.equal(name, that.name)
                && Objects.equal(description, that.description)
                && fileType == that.fileType
                && Objects.equal(icon, that.icon)
                && Objects.equal(url, that.url)
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, authorId, uploaderId, name, description, fileType, icon, url, status);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("MaterialVO{");
        builder.append("id=");
        builder.append(id);
        builder.append(", authorId=");
        builder.append(authorId);
        builder.append(", uploaderId=");
        builder.append(uploaderId);
        builder.append(", name=");
        builder.append(name);
        builder.append(", description=");
        builder.append(description);
        builder.append(", fileType=");
        builder.append(fileType);
        builder.append(", icon=");
        builder.append(icon);
        builder.append(", url=");
        builder.append(url);
        builder.append(", status=");
        builder.append(status);
        builder.append('}');
        return builder.toString();
    }

}
