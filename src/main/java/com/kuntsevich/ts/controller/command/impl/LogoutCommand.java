package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.router.Router;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    private static final String USER_HASH = "userHash";
    private static final String EMPTY_STRING = "";
    private static final String USER_EMAIL = "userEmail";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        session.invalidate();
        session.setAttribute(AttributeName.LANGUAGE, language);
        Cookie cookie = new Cookie(USER_HASH, EMPTY_STRING);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        cookie = new Cookie(USER_EMAIL, EMPTY_STRING);
        response.addCookie(cookie);
        return new Router(PagePath.HOME);
    }
}
