package com.kuntsevich.testsys.entity;

import java.io.Serializable;
import java.util.List;

public class Test implements Serializable {
    private long testId;
    private String title;
    private Subject subject;
    private String description;
    private List<Question> questions;
    private Status status;

    public Test() {
    }

    public Test(String title, Subject subject, String description, List<Question> questions, Status status) {
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.questions = questions;
        this.status = status;
    }

    public Test(long testId, String title, Subject subject, String description, List<Question> questions, Status status) {
        this.testId = testId;
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.questions = questions;
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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
        if (questions != null ? !questions.equals(test.questions) : test.questions != null) return false;
        return status != null ? status.equals(test.status) : test.status == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (testId ^ (testId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
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
        sb.append(", questions=").append(questions);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}

