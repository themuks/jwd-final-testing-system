package com.kuntsevich.ts.controller.command;

import com.kuntsevich.ts.controller.router.Router;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request);
}
