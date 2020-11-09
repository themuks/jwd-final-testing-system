package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.SessionAttribute;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionAttribute.ROLE);
        session.removeAttribute(SessionAttribute.USER_ID);
        session.removeAttribute(SessionAttribute.USER_HASH);
        session.invalidate();
        return new Router(PagePath.HOME);
    }
}
