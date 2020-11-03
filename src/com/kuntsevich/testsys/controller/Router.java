package com.kuntsevich.testsys.controller;

public class Router {
    public enum Transition{
        FORWARD,
        REDIRECT
    }

    private Transition transition = Transition.FORWARD;
    private String page;

    public Router(String page) {
        this.page = page;
    }

    public Router(String page, Transition transition) {
        this.page = page;
        this.transition = transition;
    }

    public Transition getTransition() {
        return transition;
    }

    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}