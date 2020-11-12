package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.ParameterName;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.model.service.UserService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {
    private static final Logger log = Logger.getLogger(RegistrationCommand.class);
    private static final String MESSAGE_REGISTRATION_ERROR = "message.registration.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter(ParameterName.USERNAME);
        String name = request.getParameter(ParameterName.NAME);
        String surname = request.getParameter(ParameterName.SURNAME);
        String email = request.getParameter(ParameterName.EMAIL);
        String role = request.getParameter(ParameterName.ROLE);
        String password = request.getParameter(ParameterName.PASSWORD);
        String passwordAgain = request.getParameter(ParameterName.PASSWORD_AGAIN);
        if (username == null || username.isEmpty()
                || name == null || name.isEmpty()
                || surname == null || surname.isEmpty()
                || email == null || email.isEmpty()
                || role == null || role.isEmpty()
                || password == null || password.isEmpty()
                || passwordAgain == null || passwordAgain.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_REGISTRATION_ERROR));
            return new Router(PagePath.REGISTRATION).setRedirect();
        }
        if (password.equals(passwordAgain)) {
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                UserService userService = serviceFactory.getUserService();
                if (userService.registration(username, name, surname, email, password, role)) {
                    return new Router(PagePath.LOGIN).setRedirect();
                }
            } catch (ServiceException e) {
                log.error("Register error", e);
            }
        }
        request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_REGISTRATION_ERROR));
        return new Router(PagePath.REGISTRATION).setRedirect();
    }
}
