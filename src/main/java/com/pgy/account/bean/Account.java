package com.pgy.account.bean;

import java.util.Date;

import com.google.common.base.Objects;
import com.pgy.auth.bean.Role;

/**
 * The account.
 *
 * @author Felix
 */
public class Account {
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

    public boolean isEnabled() {
        return status == AccountStatus.ENABLED;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return id == account.id
                && balance == account.balance
                && parentId == account.parentId
                && Objects.equal(accountName, account.accountName)
                && Objects.equal(accountPassword, account.accountPassword)
                && Objects.equal(username, account.username)
                && role == account.role
                && Objects.equal(email, account.email)
                && Objects.equal(phone, account.phone)
                && Objects.equal(permission, account.permission)
                && status == account.status
                && Objects.equal(beginTime, account.beginTime)
                && Objects.equal(endTime, account.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, accountName, accountPassword, username, role, balance, email, phone, permission,
                parentId, status, beginTime, endTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Account{");
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
        builder.append('}');
        return builder.toString();
    }
}
