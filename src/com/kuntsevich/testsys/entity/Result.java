package com.kuntsevich.testsys.entity;

public class Result {
    private long resultId;
    private User user;
    private Test test;
    private int points;

    public Result() {
    }

    public Result(long resultId, User user, Test test, int points) {
        this.resultId = resultId;
        this.user = user;
        this.test = test;
        this.points = points;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        if (resultId != result.resultId) return false;
        if (points != result.points) return false;
        if (user != null ? !user.equals(result.user) : result.user != null) return false;
        return test != null ? test.equals(result.test) : result.test == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (resultId ^ (resultId >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (test != null ? test.hashCode() : 0);
        result = 31 * result + points;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Result{");
        sb.append("resultId=").append(resultId);
        sb.append(", user=").append(user);
        sb.append(", test=").append(test);
        sb.append(", points=").append(points);
        sb.append('}');
        return sb.toString();
    }
}
