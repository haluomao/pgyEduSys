package com.pgy.account.bean;

import com.google.common.base.Objects;
import com.pgy.common.bean.Status;

/**
 * The account.
 *
 * @author Felix
 */
public class Account {
    private long id;
    private String accountName;
    private String accountPassword;
    private Role role;
    private long balance;
    private String email;
    private String phone;
    private long parentId;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
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
                && role == account.role
                && Objects.equal(email, account.email)
                && Objects.equal(phone, account.phone)
                && status == account.status;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, accountName, accountPassword, role, balance, email, phone, parentId, status);
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
        builder.append(", role=");
        builder.append(role);
        builder.append(", balance=");
        builder.append(balance);
        builder.append(", email=");
        builder.append(email);
        builder.append(", phone=");
        builder.append(phone);
        builder.append(", parentId=");
        builder.append(parentId);
        builder.append(", status=");
        builder.append(status);
        builder.append('}');
        return builder.toString();
    }
}
