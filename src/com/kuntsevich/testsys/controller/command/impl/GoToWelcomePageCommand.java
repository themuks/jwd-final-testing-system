package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.resourse.ConfigurationManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToWelcomePageCommand implements Command {
    private static final String PATH_PAGE_WELCOME = "path.page.welcome";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(ConfigurationManager.getProperty(PATH_PAGE_WELCOME));
        dispatcher.forward(request, response);
    }
}
