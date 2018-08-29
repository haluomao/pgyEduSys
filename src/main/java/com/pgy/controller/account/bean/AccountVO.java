package com.pgy.controller.account.bean;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pgy.account.bean.Account;
import com.pgy.account.bean.AccountConfig;
import com.pgy.account.bean.AccountStatus;
import com.pgy.auth.bean.Role;
import com.pgy.common.bean.Status;

/**
 * Account VO.
 *
 * @author Felix
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountVO {

    public static final class Builder {
        private long id;
        private String accountName;
        private String accountPassword;
        private String username;
        private Role role;
        private long balance;
        private String email;
        private String phone;
        private String permission;
        private long parentId;
        private AccountStatus status;
        private Date beginTime;
        private Date endTime;
        private AccountConfig config;
        private int teacherCount;
        private int teacherLimit;
        private int parentCount;
        private int parentLimit;
        private int storageLimit;
        private int storageUsed;

        private Builder() {
        }

        public static Builder anAccountVO() {
            return new Builder();
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withAccountName(String accountName) {
            this.accountName = accountName;
            return this;
        }

        public Builder withAccountPassword(String accountPassword) {
            this.accountPassword = accountPassword;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder withBalance(long balance) {
            this.balance = balance;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder withPermission(String permission) {
            this.permission = permission;
            return this;
        }

        public Builder withParentId(long parentId) {
            this.parentId = parentId;
            return this;
        }

        public Builder withStatus(AccountStatus status) {
            this.status = status;
            return this;
        }

        public Builder withBeginTime(Date beginTime) {
            this.beginTime = beginTime;
            return this;
        }

        public Builder withEndTime(Date endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder withConfig(AccountConfig config) {
            this.config = config;
            return this;
        }

        public Builder withTeacherCount(int teacherCount) {
            this.teacherCount = teacherCount;
            return this;
        }

        public Builder withParentCount(int parentCount) {
            this.parentCount = parentCount;
            return this;
        }

        public Builder withTeacherLimit(int teacherLimit) {
            this.teacherLimit = teacherLimit;
            return this;
        }

        public Builder withParentLimit(int parentLimit) {
            this.parentLimit = parentLimit;
            return this;
        }

        public Builder withStorageLimit(int storageLimit) {
            this.storageLimit = storageLimit;
            return this;
        }

        public Builder withStorageUsed(int storageUsed) {
            this.storageUsed = storageUsed;
            return this;
        }

        public Builder withAccount(Account account) {
            this.id = account.getId();
            this.accountName = account.getAccountName();
            this.username = account.getUsername();
            this.role = account.getRole();
            this.balance = account.getBalance();
            this.email = account.getEmail();
            this.phone = account.getPhone();
            this.permission = account.getPermission();
            this.parentId = account.getParentId();
            this.status = account.getStatus();
            this.beginTime = account.getBeginTime();
            this.endTime = account.getEndTime();
            return this;
        }

        public Builder withAccountConfig(AccountConfig accountConfig) {
            this.id = accountConfig.getAccountId();
            this.parentLimit = accountConfig.getParentLimit();
            this.teacherLimit = accountConfig.getTeacherLimit();
            this.storageLimit = accountConfig.getStorageLimit();
            this.storageUsed = accountConfig.getStorageUsed();
            return this;
        }

        public AccountVO build() {
            AccountVO accountVO = new AccountVO();
            accountVO.setId(id);
            accountVO.setAccountName(accountName);
            accountVO.setAccountPassword(accountPassword);
            accountVO.setUsername(username);
            accountVO.setRole(role);
            accountVO.setBalance(balance);
            accountVO.setEmail(email);
            accountVO.setPhone(phone);
            accountVO.setPermission(permission);
            accountVO.setParentId(parentId);
            accountVO.setStatus(status);
            accountVO.setBeginTime(beginTime);
            accountVO.setEndTime(endTime);
            accountVO.setConfig(config);
            accountVO.setTeacherCount(teacherCount);
            accountVO.setParentCount(parentCount);
            accountVO.setTeacherLimit(teacherLimit);
            accountVO.setParentLimit(parentLimit);
            accountVO.setStorageLimit(storageLimit);
            accountVO.setStorageUsed(storageUsed);
            return accountVO;
        }
    }

    private long id;
    private String accountName;
    private String accountPassword;
    private String username;
    private Role role;
    private long balance;
    private String email;
    private String phone;
    private String permission;
    private long parentId;
    private AccountStatus status;
    private Date beginTime;
    private Date endTime;
    private AccountConfig config;
    private int teacherLimit;
    private int teacherCount;
    private int parentLimit;
    private int parentCount;
    private int storageLimit;
    private int storageUsed;

    public Account buildAccount() {
        Account account = new Account();
        account.setId(id);
        account.setAccountName(accountName);
        account.setAccountPassword(accountPassword);
        account.setUsername(username);
        account.setRole(role);
        account.setBalance(balance);
        account.setEmail(email == null ? StringUtils.EMPTY : email);
        account.setPhone(phone == null ? StringUtils.EMPTY : phone);
        account.setPermission(permission == null ? StringUtils.EMPTY : permission);
        account.setParentId(parentId);
        account.setStatus(status);
        account.setBeginTime(beginTime);
        account.setEndTime(endTime);
        return account;
    }

    public AccountConfig buildAccountConfig(long accountId) {
        AccountConfig accountConfig = new AccountConfig();
        accountConfig.setAccountId(accountId);
        accountConfig.setParentLimit(parentLimit);
        accountConfig.setTeacherLimit(teacherLimit);
        accountConfig.setStorageLimit(storageLimit);
        accountConfig.setStorageUsed(0);
        accountConfig.setBeginTime(beginTime);
        accountConfig.setEndTime(endTime);
        accountConfig.setStatus(Status.ENABLED);
        return accountConfig;
    }

    public int getTeacherLimit() {
        return teacherLimit;
    }

    public void setTeacherLimit(int teacherLimit) {
        this.teacherLimit = teacherLimit;
    }

    public int getParentLimit() {
        return parentLimit;
    }

    public void setParentLimit(int parentLimit) {
        this.parentLimit = parentLimit;
    }

    public int getStorageLimit() {
        return storageLimit;
    }

    public void setStorageLimit(int storageLimit) {
        this.storageLimit = storageLimit;
    }

    public int getStorageUsed() {
        return storageUsed;
    }

    public void setStorageUsed(int storageUsed) {
        this.storageUsed = storageUsed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public AccountConfig getConfig() {
        return config;
    }

    public void setConfig(AccountConfig config) {
        this.config = config;
    }

    public int getTeacherCount() {
        return teacherCount;
    }

    public void setTeacherCount(int teacherCount) {
        this.teacherCount = teacherCount;
    }

    public int getParentCount() {
        return parentCount;
    }

    public void setParentCount(int parentCount) {
        this.parentCount = parentCount;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("AccountVO{");
        builder.append("id=");
        builder.append(id);
        builder.append(", accountName=");
        builder.append(accountName);
        builder.append(", accountPassword=");
        builder.append(accountPassword);
        builder.append(", username=");
        builder.append(username);
        builder.append(", role=");
        builder.append(role);
        builder.append(", balance=");
        builder.append(balance);
        builder.append(", email=");
        builder.append(email);
        builder.append(", phone=");
        builder.append(phone);
        builder.append(", permission=");
        builder.append(permission);
        builder.append(", parentId=");
        builder.append(parentId);
        builder.append(", status=");
        builder.append(status);
        builder.append(", beginTime=");
        builder.append(beginTime);
        builder.append(", endTime=");
        builder.append(endTime);
        builder.append(", config=");
        builder.append(config);
        builder.append(", teacherLimit=");
        builder.append(teacherLimit);
        builder.append(", teacherCount=");
        builder.append(teacherCount);
        builder.append(", parentLimit=");
        builder.append(parentLimit);
        builder.append(", parentCount=");
        builder.append(parentCount);
        builder.append(", storageLimit=");
        builder.append(storageLimit);
        builder.append(", storageUsed=");
        builder.append(storageUsed);
        builder.append('}');
        return builder.toString();
    }
}
