package com.pgy.util.bce.bos.bean;

import java.net.URL;

import com.google.common.base.Objects;

/**
 * Bos file descriptor.
 *
 * @author Felix
 */
public class BosFileDescriptor {

    public static final class Builder {
        private String id;
        private URL url;

        public Builder() {
        }

        public Builder withId(String val) {
            id = val;
            return this;
        }

        public Builder withUrl(URL val) {
            url = val;
            return this;
        }

        public BosFileDescriptor build() {
            return new BosFileDescriptor(this);
        }
    }

    private String id;
    private URL url;

    private BosFileDescriptor(Builder builder) {
        setId(builder.id);
        setUrl(builder.url);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{BosFileDescriptor, id=");
        builder.append(id);
        builder.append(", url=");
        builder.append(url);
        builder.append("}");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BosFileDescriptor)) {
            return false;
        }
        BosFileDescriptor that = (BosFileDescriptor) o;
        return Objects.equal(id, that.id)
                && Objects.equal(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, url);
    }
}
