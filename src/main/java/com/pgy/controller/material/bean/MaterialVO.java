package com.pgy.controller.material.bean;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pgy.material.bean.FileType;
import com.pgy.material.bean.Material;
import com.pgy.material.bean.MaterialStatus;
import com.pgy.material.bean.PublicLevel;
import com.pgy.material.bean.TeachType;

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
        private TeachType teachType;
        private String icon = StringUtils.EMPTY;
        private String url;
        private String downloadUrl;
        private int materialSize;
        private int iconSize;
        private long categoryId;
        private long gradeId;
        private MaterialStatus status;
        private PublicLevel publicLevel;

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

        public Builder withTeachType(TeachType teachType) {
            this.teachType = teachType;
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

        public Builder withDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
            return this;
        }

        public Builder withMaterialSize(int materialSize) {
            this.materialSize = materialSize;
            return this;
        }

        public Builder withIconSize(int iconSize) {
            this.iconSize = iconSize;
            return this;
        }

        public Builder withPublicLevel(PublicLevel publicLevel) {
            this.publicLevel = publicLevel;
            return this;
        }

        public Builder withStatus(MaterialStatus status) {
            this.status = status;
            return this;
        }

        public Builder withGradeId(long gradeId) {
            this.gradeId = gradeId;
            return this;
        }

        public Builder withCategoryId(long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder withMaterial(Material material) {
            this.id = material.getId();
            this.authorId = material.getAuthorId();
            this.uploaderId = material.getUploaderId();
            this.name = material.getName();
            this.description = material.getDescription();
            this.fileType = material.getFileType();
            this.teachType = material.getTeachType();
            this.icon = material.getIcon();
            this.url = material.getUrl();
            this.downloadUrl = material.getDownloadUrl();
            this.status = material.getStatus();
            this.publicLevel = publicLevel;
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
            materialVO.setTeachType(teachType);
            materialVO.setIcon(icon);
            materialVO.setUrl(url);
            materialVO.setDownloadUrl(downloadUrl);
            materialVO.setMaterialSize(materialSize);
            materialVO.setCategoryId(categoryId);
            materialVO.setGradeId(gradeId);
            materialVO.setStatus(status);
            materialVO.setPublicLevel(publicLevel);
            return materialVO;
        }
    }

    private long id;
    private long authorId;
    private long uploaderId;
    private String name;
    private String description;
    private FileType fileType;
    private TeachType teachType;
    @JsonProperty("avatar")
    private String icon;
    @JsonProperty("material")
    private String url;
    private String downloadUrl;
    private int materialSize;
    @JsonProperty("avatarSize")
    private int iconSize;
    private long categoryId;
    private long gradeId;
    private MaterialStatus status;
    private PublicLevel publicLevel;

    public Material buildMaterial() {
        Material material = new Material();
        material.setId(id);
        material.setAuthorId(authorId);
        material.setUploaderId(uploaderId);
        material.setIcon(icon == null ? StringUtils.EMPTY : icon);
        material.setUrl(url);
        material.setDownloadUrl(downloadUrl);
        material.setFileType(fileType);
        material.setTeachType(teachType);
        material.setName(name);
        material.setDescription(description);
        material.setStatus(status);
        material.setPublicLevel(publicLevel);
        return material;
    }

    public PublicLevel getPublicLevel() {
        return publicLevel;
    }

    public void setPublicLevel(PublicLevel publicLevel) {
        this.publicLevel = publicLevel;
    }

    public int getTotalSize() {
        return materialSize + iconSize;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getMaterialSize() {
        return materialSize;
    }

    public void setMaterialSize(int materialSize) {
        this.materialSize = materialSize;
    }

    public int getIconSize() {
        return iconSize;
    }

    public void setIconSize(int iconSize) {
        this.iconSize = iconSize;
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

    public TeachType getTeachType() {
        return teachType;
    }

    public void setTeachType(TeachType teachType) {
        this.teachType = teachType;
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
        builder.append(", teachType=");
        builder.append(teachType);
        builder.append(", icon=");
        builder.append(icon);
        builder.append(", url=");
        builder.append(url);
        builder.append(", downloadUrl=");
        builder.append(downloadUrl);
        builder.append(", materialSize=");
        builder.append(materialSize);
        builder.append(", iconSize=");
        builder.append(iconSize);
        builder.append(", categoryId=");
        builder.append(categoryId);
        builder.append(", gradeId=");
        builder.append(gradeId);
        builder.append(", status=");
        builder.append(status);
        builder.append(", publicLevel=");
        builder.append(publicLevel);
        builder.append('}');
        return builder.toString();
    }
}
