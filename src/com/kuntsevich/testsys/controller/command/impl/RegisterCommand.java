package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.exception.ServiceException;
import com.kuntsevich.testsys.model.service.UserService;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements Command {
    private static final Logger log = Logger.getLogger(RegisterCommand.class);
    private static final String JSP_SUCCESS_PAGE = "/WEB-INF/jsp/login.jsp";
    private static final String JSP_ERROR_PAGE = "/WEB-INF/jsp/error.jsp";
    private static final String PARAM_NAME_USERNAME = "username";
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_SURNAME = "surname";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = JSP_ERROR_PAGE;
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        String username = request.getParameter(PARAM_NAME_USERNAME);
        String name = request.getParameter(PARAM_NAME_NAME);
        String surname = request.getParameter(PARAM_NAME_SURNAME);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        System.out.println(username + name + surname + email + password);
        try {
            if (userService.register(username, name, surname, email, password)) {
                // TODO: 05.10.2020 Success message
                page = JSP_SUCCESS_PAGE;
            } else {
                // TODO: 05.10.2020 Error message
            }
        } catch (ServiceException e) {
            log.error("Register error", e);
        }
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
