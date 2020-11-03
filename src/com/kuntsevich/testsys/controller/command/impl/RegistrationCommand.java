package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.PagePath;
import com.kuntsevich.testsys.controller.RequestParameter;
import com.kuntsevich.testsys.controller.Router;
import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.manager.ConfigurationManager;
import com.kuntsevich.testsys.controller.manager.MessageManager;
import com.kuntsevich.testsys.model.service.UserService;
import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private static final Logger log = Logger.getLogger(RegistrationCommand.class);
    private static final String MESSAGE_REGISTRATION_ERROR = "message.registration.error";
    private static final String REGISTRATION_PAGE_SUCCESS = "registration.success";

    @Override
    public Router execute(HttpServletRequest request) {
        String page;
        String username = request.getParameter(RequestParameter.USERNAME);
        String name = request.getParameter(RequestParameter.NAME);
        String surname = request.getParameter(RequestParameter.SURNAME);
        String email = request.getParameter(RequestParameter.EMAIL);
        String role = request.getParameter(RequestParameter.ROLE);
        String password = request.getParameter(RequestParameter.PASSWORD);
        String passwordAgain = request.getParameter(RequestParameter.PASSWORD_AGAIN);
        if (password.equals(passwordAgain)) {
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                UserService userService = serviceFactory.getUserService();
                if (userService.registration(username, name, surname, email, password, role)) {
                    page = PagePath.LOGIN;
                } else {
                    page = PagePath.REGISTRATION;
                    request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_REGISTRATION_ERROR));
                }
            } catch (ServiceException e) {
                page = PagePath.REGISTRATION;
                request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_REGISTRATION_ERROR));
                log.error("Register error", e);
            }
        } else {
            page = PagePath.REGISTRATION;
            request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_REGISTRATION_ERROR));
        }
        return new Router(page, Router.Transition.REDIRECT);
    }
}
