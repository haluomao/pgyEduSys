package com.pgy.controller.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Objects;

/**
 * The upload result.
 *
 * @author Felix
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadResult {

    public static final class Builder {
        private String url;
        private String mimeType;
        private String path;
        private String downloadUrl;
        private int storageUsed;

        private Builder() {
        }

        public static Builder anUploadResult() {
            return new Builder();
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withMimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        public Builder withPath(String path) {
            this.path = path;
            return this;
        }

        public Builder withDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
            return this;
        }

        public Builder withStorageUsed(int storageUsed) {
            this.storageUsed = storageUsed;
            return this;
        }

        public UploadResult build() {
            UploadResult uploadResult = new UploadResult();
            uploadResult.setUrl(url);
            uploadResult.setMimeType(mimeType);
            uploadResult.setPath(path);
            uploadResult.setDownloadUrl(downloadUrl);
            uploadResult.setStorageUsed(storageUsed);
            return uploadResult;
        }
    }

    private String url;
    private String mimeType;
    @JsonIgnore
    private String path;
    private String downloadUrl;
    private int storageUsed;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getStorageUsed() {
        return storageUsed;
    }

    public void setStorageUsed(int storageUsed) {
        this.storageUsed = storageUsed;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("UploadResult{");
        builder.append("url=");
        builder.append(url);
        builder.append(", mimeType=");
        builder.append(mimeType);
        builder.append(", path=");
        builder.append(path);
        builder.append(", downloadUrl=");
        builder.append(downloadUrl);
        builder.append(", storageUsed=");
        builder.append(storageUsed);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadResult)) {
            return false;
        }
        UploadResult result = (UploadResult) o;
        return storageUsed == result.storageUsed
                && Objects.equal(url, result.url)
                && Objects.equal(mimeType, result.mimeType)
                && Objects.equal(path, result.path)
                && Objects.equal(downloadUrl, result.downloadUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url, mimeType, path, downloadUrl, storageUsed);
    }
}
