package com.kuntsevich.testsys.entity;

public class Test {
    private long testId;
    private String title;
    private Subject subject;
    private String description;
    private Status status;

    public Test() {
    }

    public Test(long testId, String title, Subject subject, String description, Status status) {
        this.testId = testId;
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.status = status;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        Test test = (Test) o;

        if (testId != test.testId) return false;
        if (title != null ? !title.equals(test.title) : test.title != null) return false;
        if (subject != null ? !subject.equals(test.subject) : test.subject != null) return false;
        if (description != null ? !description.equals(test.description) : test.description != null) return false;
        return status == test.status;
    }

    @Override
    public int hashCode() {
        int result = (int) (testId ^ (testId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Test{");
        sb.append("testId=").append(testId);
        sb.append(", title='").append(title).append('\'');
        sb.append(", subject=").append(subject);
        sb.append(", description='").append(description).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}

