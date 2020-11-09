package com.kuntsevich.ts.entity;

import java.io.Serializable;

public class Answer implements Serializable {
    private long answerId;
    private String text;
    private boolean isCorrect;

    public Answer() {
    }

    public Answer(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public Answer(long answerId, String text, boolean isCorrect) {
        this.answerId = answerId;
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (answerId != answer.answerId) return false;
        if (isCorrect != answer.isCorrect) return false;
        return text != null ? text.equals(answer.text) : answer.text == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (answerId ^ (answerId >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (isCorrect ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Answer{");
        sb.append("answerId=").append(answerId);
        sb.append(", text='").append(text).append('\'');
        sb.append(", isCorrect=").append(isCorrect);
        sb.append('}');
        return sb.toString();
    }
}
