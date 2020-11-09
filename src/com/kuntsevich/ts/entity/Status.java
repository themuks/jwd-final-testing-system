package com.kuntsevich.ts.entity;

import java.io.Serializable;

public class Status implements Serializable {
    private long statusId;
    private String name;

    public Status() {
    }

    public Status(String name) {
        this.name = name;
    }

    public Status(long statusId, String name) {
        this.statusId = statusId;
        this.name = name;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Status status = (Status) o;

        if (statusId != status.statusId) return false;
        return name != null ? name.equals(status.name) : status.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (statusId ^ (statusId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Status{");
        sb.append("statusId=").append(statusId);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
