package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.RequestParameter;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowTestCreateCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(RequestParameter.TEMPLATE_PATH, PagePath.TEST_CREATE_TEMPLATE);
        return new Router(PagePath.HOME);
    }
}
