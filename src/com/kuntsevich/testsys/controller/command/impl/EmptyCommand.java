package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.resourse.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmptyCommand implements Command {
    private static final String PATH_PAGE_INDEX = "path.page.index";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = ConfigurationManager.getProperty(PATH_PAGE_INDEX);
        response.sendRedirect(request.getContextPath() + page);
    }
}
