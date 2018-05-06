package com.pgy.account.bean;

import java.util.Date;

import com.google.common.base.Objects;
import com.pgy.common.bean.Status;

/**
 * The account config.
 *
 * @author Felix
 */
public class AccountConfig {
    private long id;
    private long accountId;
    private int teacherLimit;
    private int parentLimit;
    private int storageLimit;
    private int storageUsed;
    private Status status;
    private Date beginTime;
    private Date endTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountConfig)) {
            return false;
        }
        AccountConfig that = (AccountConfig) o;
        return id == that.id
                && accountId == that.accountId
                && teacherLimit == that.teacherLimit
                && parentLimit == that.parentLimit
                && storageLimit == that.storageLimit
                && storageUsed == that.storageUsed
                && status == that.status
                && Objects.equal(beginTime, that.beginTime)
                && Objects.equal(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, accountId, teacherLimit, parentLimit, storageLimit, storageUsed, status,
                beginTime, endTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("AccountConfig{");
        builder.append("id=");
        builder.append(id);
        builder.append(", accountId=");
        builder.append(accountId);
        builder.append(", teacherLimit=");
        builder.append(teacherLimit);
        builder.append(", parentLimit=");
        builder.append(parentLimit);
        builder.append(", storageLimit=");
        builder.append(storageLimit);
        builder.append(", storageUsed=");
        builder.append(storageUsed);
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
