package com.pgy.uploadlog.bean;

import com.google.common.base.Objects;
import com.pgy.material.bean.MaterialStatus;

/**
 * The upload log.
 *
 * @author Felix
 */
public class UploadLog {

    private long id;
    private long uploaderId;
    private String filename;
    private String fileType;
    private String signature;
    private String path;
    private String url;
    private int size;
    private MaterialStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(long uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("UploadLog{");
        builder.append("id=");
        builder.append(id);
        builder.append(", uploaderId=");
        builder.append(uploaderId);
        builder.append(", filename=");
        builder.append(filename);
        builder.append(", fileType=");
        builder.append(fileType);
        builder.append(", signature=");
        builder.append(signature);
        builder.append(", path=");
        builder.append(path);
        builder.append(", url=");
        builder.append(url);
        builder.append(", size=");
        builder.append(size);
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
        if (!(o instanceof UploadLog)) {
            return false;
        }
        UploadLog uploadLog = (UploadLog) o;
        return id == uploadLog.id
                && uploaderId == uploadLog.uploaderId
                && size == uploadLog.size
                && Objects.equal(filename, uploadLog.filename)
                && Objects.equal(fileType, uploadLog.fileType)
                && Objects.equal(signature, uploadLog.signature)
                && Objects.equal(path, uploadLog.path)
                && Objects.equal(url, uploadLog.url)
                && status == uploadLog.status;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, uploaderId, filename, fileType, signature, path, url, size, status);
    }

    public static final class Builder {
        private long id;
        private long uploaderId;
        private String filename;
        private String fileType;
        private String signature;
        private String path;
        private String url;
        private int size;
        private MaterialStatus status;

        private Builder() {
        }

        public static Builder anUploadLog() {
            return new Builder();
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withUploaderId(long uploaderId) {
            this.uploaderId = uploaderId;
            return this;
        }

        public Builder withFilename(String filename) {
            this.filename = filename;
            return this;
        }

        public Builder withFileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public Builder withSignature(String signature) {
            this.signature = signature;
            return this;
        }

        public Builder withPath(String path) {
            this.path = path;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withSize(int size) {
            this.size = size;
            return this;
        }

        public Builder withStatus(MaterialStatus status) {
            this.status = status;
            return this;
        }

        public UploadLog build() {
            UploadLog uploadLog = new UploadLog();
            uploadLog.setId(id);
            uploadLog.setUploaderId(uploaderId);
            uploadLog.setFilename(filename);
            uploadLog.setFileType(fileType);
            uploadLog.setSignature(signature);
            uploadLog.setPath(path);
            uploadLog.setUrl(url);
            uploadLog.setSize(size);
            uploadLog.setStatus(status);
            return uploadLog;
        }
    }
}
