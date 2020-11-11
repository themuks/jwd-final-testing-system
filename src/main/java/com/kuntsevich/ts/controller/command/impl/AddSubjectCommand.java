package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddSubjectCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        return new Router(PagePath.HOME);
    }
}
