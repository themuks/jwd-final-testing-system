package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.manager.ConfigurationManager;
import com.kuntsevich.testsys.controller.manager.MessageManager;
import com.kuntsevich.testsys.entity.Credential;
import com.kuntsevich.testsys.entity.User;
import com.kuntsevich.testsys.model.service.UserService;
import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_REMEMBER_ME = "remember-me";
    private static final String USER_HASH = "user_hash";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE_LOGIN_ERROR = "message.login.error";
    private static final String LOGIN_PAGE_SUCCESS = "login.success";
    private static final String LOGIN_PAGE_ERROR = "login.error";
    private static final String LOGIN_SERVER_ERROR = "message.login.server.error";
    private static final String USER_ID = "userId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = ConfigurationManager.getProperty(LOGIN_PAGE_ERROR);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String rememberMe = request.getParameter(PARAM_NAME_REMEMBER_ME);
        boolean isSuccessful = false;
        try {
            Optional<Credential> optionalCredential = userService.checkLogin(email, password);
            if (optionalCredential.isPresent()) {
                Credential credential = optionalCredential.get();
                HttpSession session = request.getSession();
                session.setAttribute(USER_ID, credential.getUserId());
                session.setAttribute(USER_HASH, credential.getUserHash());
                page = ConfigurationManager.getProperty(LOGIN_PAGE_SUCCESS);
                isSuccessful = true;
            } else {
                request.setAttribute(ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_LOGIN_ERROR));
            }
        } catch (ServiceException e) {
            request.setAttribute(ERROR_MESSAGE, MessageManager.getProperty(LOGIN_SERVER_ERROR));
            log.error("Login check error", e);
        }
        if (isSuccessful) {
            String path = request.getContextPath() + page;
            response.sendRedirect(path);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }
}
