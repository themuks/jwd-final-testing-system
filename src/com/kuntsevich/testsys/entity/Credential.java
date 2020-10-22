package com.kuntsevich.testsys.entity;

public class Credential {
    private final long userId;
    private final String userHash;

    public Credential(long userId, String userHash) {
        this.userId = userId;
        this.userHash = userHash;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserHash() {
        return userHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Credential that = (Credential) o;

        if (userId != that.userId) return false;
        return userHash != null ? userHash.equals(that.userHash) : that.userHash == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (userHash != null ? userHash.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Credential{");
        sb.append("userId=").append(userId);
        sb.append(", userHash='").append(userHash).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
