package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.router.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return null;
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // todo: 09.10.2020 create this method
        /*HttpSession session = request.getSession();
        Object language = session.getAttribute(LANGUAGE_SESSION_ATTRIBUTE);
        if (language != null) {
            if (language instanceof String) {
                if (language.equals(RUSSIAN_LANGUAGE)) {
                    session.setAttribute(LANGUAGE_SESSION_ATTRIBUTE, ENGLISH_LANGUAGE);
                } else {
                    session.setAttribute(LANGUAGE_SESSION_ATTRIBUTE, RUSSIAN_LANGUAGE);
                }
            }
        }
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher();
        dispatcher.forward(request, response);*/
    }
}
