package com.kuntsevich.ts.entity;

import java.io.Serializable;

public class User implements Serializable {
    private long userId;
    private String username;
    private String name;
    private String surname;
    private String emailHash;
    private String passwordHash;
    private String userHash;
    private Role role;
    private Status status;

    public User() {
    }

    public User(String username, String name, String surname, String emailHash, String passwordHash, String userHash, Role role, Status status) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.emailHash = emailHash;
        this.passwordHash = passwordHash;
        this.userHash = userHash;
        this.role = role;
        this.status = status;
    }

    public User(long userId, String username, String name, String surname, String emailHash, String passwordHash, String userHash, Role role, Status status) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.emailHash = emailHash;
        this.passwordHash = passwordHash;
        this.userHash = userHash;
        this.role = role;
        this.status = status;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmailHash() {
        return emailHash;
    }

    public void setEmailHash(String emailHash) {
        this.emailHash = emailHash;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUserHash() {
        return userHash;
    }

    public void setUserHash(String userHash) {
        this.userHash = userHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (emailHash != null ? !emailHash.equals(user.emailHash) : user.emailHash != null) return false;
        if (passwordHash != null ? !passwordHash.equals(user.passwordHash) : user.passwordHash != null) return false;
        if (userHash != null ? !userHash.equals(user.userHash) : user.userHash != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        return status != null ? status.equals(user.status) : user.status == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (emailHash != null ? emailHash.hashCode() : 0);
        result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
        result = 31 * result + (userHash != null ? userHash.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userId=").append(userId);
        sb.append(", username='").append(username).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", emailHash='").append(emailHash).append('\'');
        sb.append(", passwordHash='").append(passwordHash).append('\'');
        sb.append(", userHash='").append(userHash).append('\'');
        sb.append(", role=").append(role);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
