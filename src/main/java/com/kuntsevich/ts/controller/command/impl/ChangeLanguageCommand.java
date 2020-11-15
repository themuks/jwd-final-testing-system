package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.ParameterName;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {
    private static final String RUSSIAN = "ru";
    private static final String MESSAGE_LANGUAGE_ERROR = "message.language.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        MessageManager.setLanguage(language);
        if (language == null || language.isEmpty()) {
            session.setAttribute(AttributeName.LANGUAGE, RUSSIAN);
        }
        String previousPage = (String) session.getAttribute(AttributeName.PREVIOUS_PAGE);
        if (previousPage == null || previousPage.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_LANGUAGE_ERROR));
            return new Router(PagePath.HOME);
        }
        String newLanguage = request.getParameter(ParameterName.LANGUAGE);
        if (newLanguage == null || newLanguage.isEmpty()) {
            return new Router(previousPage).setRedirect();
        }
        session.setAttribute(AttributeName.LANGUAGE, newLanguage);
        return new Router(PagePath.HOME);
    }
}
