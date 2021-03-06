package com.kuntsevich.ts.entity;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private long questionId;
    private String text;
    private List<Answer> answers;
    private int points;

    public Question() {
    }

    public Question(String text, List<Answer> answers, int points) {
        this.text = text;
        this.answers = answers;
        this.points = points;
    }

    public Question(long questionId, String text, List<Answer> answers, int points) {
        this.questionId = questionId;
        this.text = text;
        this.answers = answers;
        this.points = points;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (questionId != question.questionId) return false;
        if (points != question.points) return false;
        if (text != null ? !text.equals(question.text) : question.text != null) return false;
        return answers != null ? answers.equals(question.answers) : question.answers == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (questionId ^ (questionId >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        result = 31 * result + points;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Question{");
        sb.append("questionId=").append(questionId);
        sb.append(", text='").append(text).append('\'');
        sb.append(", answers=").append(answers);
        sb.append(", points=").append(points);
        sb.append('}');
        return sb.toString();
    }
}
