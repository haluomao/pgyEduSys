package com.pgy.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Objects;
import com.pgy.controller.bean.RestResultResponse;

/**
 * The rest response.
 *
 * @param <R> The result type.
 * @param <M> The message type.
 * @param <P> The pageable type.
 *
 * @author Felix
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<R, M, P> implements RestPageResponse<P>, RestResultResponse<R> {

    static final class Builder<R, M, P> {
        private boolean success;
        private R result;
        private M message;
        private RestPage<P> page;

        public Builder<R, M, P> withSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public Builder<R, M, P> withResult(R result) {
            this.result = result;
            return this;
        }

        public Builder<R, M, P> withMessage(M message) {
            this.message = message;
            return this;
        }

        public Builder<R, M, P> withPage(RestPage<P> page) {
            this.page = page;
            return this;
        }

        public RestResponse<R, M, P> build() {
            RestResponse<R, M, P> response = new RestResponse<R, M, P>();
            response.success = success;
            response.result = result;
            response.message = message;
            response.page = page;
            return response;
        }
    }

    RestResponse() {
    }

    private boolean success;
    private R result = null;
    private M message = null;
    private RestPage<P> page = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public M getMessage() {
        return message;
    }

    public void setMessage(M message) {
        this.message = message;
    }

    public RestPage<P> getPage() {
        return page;
    }

    public void setPage(RestPage<P> page) {
        this.page = page;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{RestResponse,success=");
        builder.append(success);
        builder.append(",result=");
        builder.append(result);
        builder.append(",message=");
        builder.append(message);
        builder.append(",page=");
        builder.append(page);
        builder.append("}");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RestResponse)) {
            return false;
        }
        RestResponse<?, ?, ?> that = (RestResponse<?, ?, ?>) o;
        return success == that.success
                && Objects.equal(result, that.result)
                && Objects.equal(message, that.message)
                && Objects.equal(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(success, result, message, page);
    }
}
