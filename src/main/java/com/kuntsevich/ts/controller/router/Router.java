package com.kuntsevich.ts.controller.router;

public class Router {
    private String page;
    private TransitionType transitionType = TransitionType.FORWARD;

    private Router() {
    }

    public Router(String page) {
        this.page = page;
    }

    public Router(String page, TransitionType transitionType) {
        this.page = page;
        this.transitionType = transitionType;
    }

    public String getPage() {
        return page;
    }

    public TransitionType getTransitionType() {
        return transitionType;
    }

    public Router setForward() {
        this.transitionType = TransitionType.FORWARD;
        return this;
    }

    public Router setRedirect() {
        this.transitionType = TransitionType.REDIRECT;
        return this;
    }

    public Router setInclude() {
        this.transitionType = TransitionType.INCLUDE;
        return this;
    }
}