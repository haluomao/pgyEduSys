

package com.pgy.auth.bean;

import org.apache.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Validation response returned by external authentication.
 *
 * @author Felix
 */
public class ExternalAuthResponse {

    public static class User {
        private String name;
        private String email;
        @JsonProperty("ad_role")
        private String adRole;
        private String trade;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAdRole() {
            return adRole;
        }

        public void setAdRole(String adRole) {
            this.adRole = adRole;
        }

        public String getTrade() {
            return trade;
        }

        public void setTrade(String trade) {
            this.trade = trade;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder("User{");
            builder.append("name=");
            builder.append(name);
            builder.append(", email=");
            builder.append(email);
            builder.append(", adRole=");
            builder.append(adRole);
            builder.append(", trade=");
            builder.append(trade);
            builder.append('}');
            return builder.toString();
        }
    }

    private int code;
    private String msg;
    private User data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public boolean isValid() {
        return code == HttpStatus.SC_OK;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("ExternalAuthResponse{");
        builder.append("code=");
        builder.append(code);
        builder.append(", msg=");
        builder.append(msg);
        builder.append(", data=");
        builder.append(data);
        builder.append('}');
        return builder.toString();
    }
}