package com.kuntsevich.ts.entity;

import java.io.Serializable;

public class Role implements Serializable {
    private long roleId;
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(long roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
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

        Role role = (Role) o;

        if (roleId != role.roleId) return false;
        return name != null ? name.equals(role.name) : role.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Role{");
        sb.append("roleId=").append(roleId);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
