package com.kuntsevich.ts.entity;

public class Credential {
    private final String userHash;
    private final String emailHash;
    private long userId;

    public Credential(String userHash, String emailHash) {
        this.userHash = userHash;
        this.emailHash = emailHash;
    }

    public Credential(long userId, String userHash, String emailHash) {
        this.userId = userId;
        this.userHash = userHash;
        this.emailHash = emailHash;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserHash() {
        return userHash;
    }

    public String getEmailHash() {
        return emailHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Credential that = (Credential) o;

        if (userId != that.userId) return false;
        if (userHash != null ? !userHash.equals(that.userHash) : that.userHash != null) return false;
        return emailHash != null ? emailHash.equals(that.emailHash) : that.emailHash == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (userHash != null ? userHash.hashCode() : 0);
        result = 31 * result + (emailHash != null ? emailHash.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Credential{");
        sb.append("userId=").append(userId);
        sb.append(", userHash='").append(userHash).append('\'');
        sb.append(", userEmail='").append(emailHash).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
