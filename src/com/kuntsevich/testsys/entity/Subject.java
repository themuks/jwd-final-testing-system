package com.kuntsevich.testsys.entity;

import java.io.Serializable;

public class Subject implements Serializable {
    private long subjectId;
    private String name;
    private String description;

    public Subject() {
    }

    public Subject(long subjectId, String name, String description) {
        this.subjectId = subjectId;
        this.name = name;
        this.description = description;
    }

    public Subject(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (subjectId != subject.subjectId) return false;
        if (name != null ? !name.equals(subject.name) : subject.name != null) return false;
        return description != null ? description.equals(subject.description) : subject.description == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (subjectId ^ (subjectId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Subject{");
        sb.append("subjectId=").append(subjectId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
