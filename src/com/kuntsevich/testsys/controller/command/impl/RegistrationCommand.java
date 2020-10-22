package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.manager.ConfigurationManager;
import com.kuntsevich.testsys.controller.manager.MessageManager;
import com.kuntsevich.testsys.model.service.UserService;
import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationCommand implements Command {
    private static final Logger log = Logger.getLogger(RegistrationCommand.class);
    private static final String PARAM_NAME_USERNAME = "username";
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_SURNAME = "surname";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_PASSWORD_AGAIN = "password-again";
    private static final String MESSAGE_REGISTRATION_ERROR = "message.registration.error";
    private static final String MESSAGE_REGISTRATION_PASSWORD_ERROR = "message.registration.password.error";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String REGISTRATION_PAGE_ERROR = "registration.error";
    private static final String REGISTRATION_PAGE_SUCCESS = "registration.success";
    private static final String PARAM_NAME_ROLE = "role";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = ConfigurationManager.getProperty(REGISTRATION_PAGE_ERROR);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        String username = request.getParameter(PARAM_NAME_USERNAME);
        String name = request.getParameter(PARAM_NAME_NAME);
        String surname = request.getParameter(PARAM_NAME_SURNAME);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String role = request.getParameter(PARAM_NAME_ROLE);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String passwordAgain = request.getParameter(PARAM_NAME_PASSWORD_AGAIN);
        boolean isSuccessful = false;
        if (password.equals(passwordAgain)) {
            try {
                if (userService.register(username, name, surname, email, password, role)) {
                    page = ConfigurationManager.getProperty(REGISTRATION_PAGE_SUCCESS);
                    isSuccessful = true;
                } else {
                    request.setAttribute(ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_REGISTRATION_ERROR));
                }
            } catch (ServiceException e) {
                request.setAttribute(ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_REGISTRATION_ERROR));
                log.error("Register error", e);
            }
        } else {
            request.setAttribute(ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_REGISTRATION_ERROR));
        }
        if (isSuccessful) {
            String path = request.getContextPath() + page;
            response.sendRedirect(path);
        } else {
            String path = request.getContextPath() + page;
            response.sendRedirect(path);
        }
    }
}
