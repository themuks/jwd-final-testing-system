package com.kuntsevich.ts.entity;

public class Credential {
    private final String userHash;
    private final String email;
    private long userId;

    public Credential(String userHash, String email) {
        this.userHash = userHash;
        this.email = email;
    }

    public Credential(long userId, String userHash, String email) {
        this.userId = userId;
        this.userHash = userHash;
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserHash() {
        return userHash;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Credential that = (Credential) o;

        if (userId != that.userId) return false;
        if (userHash != null ? !userHash.equals(that.userHash) : that.userHash != null) return false;
        return email != null ? email.equals(that.email) : that.email == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (userHash != null ? userHash.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Credential{");
        sb.append("userId=").append(userId);
        sb.append(", userHash='").append(userHash).append('\'');
        sb.append(", userEmail='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
