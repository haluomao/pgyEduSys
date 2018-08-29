package com.pgy.material.bean;

import com.google.common.base.Objects;

/**
 * The material.
 *
 * @author Felix
 */
public class Material {
    private long id;
    private String name;
    private String description;
    private FileType fileType;
    private TeachType teachType;
    private String url;
    private String downloadUrl;
    private String icon;
    private long authorId;
    private long uploaderId;
    private MaterialStatus status;
    private PublicLevel publicLevel;

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

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public MaterialStatus getStatus() {
        return status;
    }

    public void setStatus(MaterialStatus status) {
        this.status = status;
    }

    public PublicLevel getPublicLevel() {
        return publicLevel;
    }

    public void setPublicLevel(PublicLevel publicLevel) {
        this.publicLevel = publicLevel;
    }

    public TeachType getTeachType() {
        return teachType;
    }

    public void setTeachType(TeachType teachType) {
        this.teachType = teachType;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Material{");
        builder.append("id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", description=");
        builder.append(description);
        builder.append(", fileType=");
        builder.append(fileType);
        builder.append(", teachType=");
        builder.append(teachType);
        builder.append(", url=");
        builder.append(url);
        builder.append(", downloadUrl=");
        builder.append(downloadUrl);
        builder.append(", icon=");
        builder.append(icon);
        builder.append(", authorId=");
        builder.append(authorId);
        builder.append(", uploaderId=");
        builder.append(uploaderId);
        builder.append(", status=");
        builder.append(status);
        builder.append(", publicLevel=");
        builder.append(publicLevel);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Material)) {
            return false;
        }
        Material material = (Material) o;
        return id == material.id
                && authorId == material.authorId
                && uploaderId == material.uploaderId
                && Objects.equal(name, material.name)
                && Objects.equal(description, material.description)
                && fileType == material.fileType
                && teachType == material.teachType
                && Objects.equal(url, material.url)
                && Objects.equal(downloadUrl, material.downloadUrl)
                && Objects.equal(icon, material.icon)
                && status == material.status
                && publicLevel == material.publicLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, fileType, teachType, url, downloadUrl, icon, authorId, uploaderId, status, publicLevel);
    }
}
