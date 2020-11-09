package com.kuntsevich.ts.entity;

import java.io.Serializable;

public class Result implements Serializable {
    private long resultId;
    private User user;
    private Test test;
    private int points;
    private int correctAnswers;
    private int totalPoints;
    private boolean isTestPassed;

    public Result() {
    }

    public Result(User user, Test test, int points, int correctAnswers, int totalPoints, boolean isTestPassed) {
        this.user = user;
        this.test = test;
        this.points = points;
        this.correctAnswers = correctAnswers;
        this.totalPoints = totalPoints;
        this.isTestPassed = isTestPassed;
    }

    public Result(long resultId, User user, Test test, int points, int correctAnswers, int totalPoints, boolean isTestPassed) {
        this.resultId = resultId;
        this.user = user;
        this.test = test;
        this.points = points;
        this.correctAnswers = correctAnswers;
        this.totalPoints = totalPoints;
        this.isTestPassed = isTestPassed;
    }

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public boolean isTestPassed() {
        return isTestPassed;
    }

    public void setTestPassed(boolean testPassed) {
        isTestPassed = testPassed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        if (resultId != result.resultId) return false;
        if (points != result.points) return false;
        if (correctAnswers != result.correctAnswers) return false;
        if (totalPoints != result.totalPoints) return false;
        if (isTestPassed != result.isTestPassed) return false;
        if (user != null ? !user.equals(result.user) : result.user != null) return false;
        return test != null ? test.equals(result.test) : result.test == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (resultId ^ (resultId >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (test != null ? test.hashCode() : 0);
        result = 31 * result + points;
        result = 31 * result + correctAnswers;
        result = 31 * result + totalPoints;
        result = 31 * result + (isTestPassed ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Result{");
        sb.append("resultId=").append(resultId);
        sb.append(", user=").append(user);
        sb.append(", test=").append(test);
        sb.append(", points=").append(points);
        sb.append(", correctAnswers=").append(correctAnswers);
        sb.append(", totalPoints=").append(totalPoints);
        sb.append(", isTestPassed=").append(isTestPassed);
        sb.append('}');
        return sb.toString();
    }
}
