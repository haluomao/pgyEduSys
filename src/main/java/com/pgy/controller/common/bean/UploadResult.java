package com.pgy.controller.common.bean;

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

        public UploadResult build() {
            UploadResult uploadResult = new UploadResult();
            uploadResult.setUrl(url);
            uploadResult.setMimeType(mimeType);
            return uploadResult;
        }
    }

    private String url;
    private String mimeType;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadResult)) {
            return false;
        }
        UploadResult that = (UploadResult) o;
        return Objects.equal(url, that.url)
                && Objects.equal(mimeType, that.mimeType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url, mimeType);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("UploadResult{");
        builder.append("url=");
        builder.append(url);
        builder.append(", mimeType=");
        builder.append(mimeType);
        builder.append('}');
        return builder.toString();
    }
}
