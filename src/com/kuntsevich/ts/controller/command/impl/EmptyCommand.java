package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.router.Router;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.WELCOME).setRedirect();
    }
}
