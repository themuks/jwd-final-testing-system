package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.entity.User;
import com.kuntsevich.testsys.exception.ServiceException;
import com.kuntsevich.testsys.model.service.UserService;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;
import com.kuntsevich.testsys.resourse.MessageManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);
    private static final String JSP_SUCCESS_PAGE = "/WEB-INF/jsp/main.jsp";
    private static final String JSP_ERROR_PAGE = "/WEB-INF/jsp/login.jsp";
    private final static String PARAM_NAME_EMAIL = "email";
    private final static String PARAM_NAME_PASSWORD = "password";
    private static final String USER_HASH = "user_hash";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE_LOGIN_ERROR = "message.loginerror";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = JSP_ERROR_PAGE;
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        try {
            Optional<User> optionalUser = userService.checkLogin(email, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Cookie cookie = new Cookie(USER_HASH, user.getUserHash());
                response.addCookie(cookie);
                page = JSP_SUCCESS_PAGE;
            } else {
                request.setAttribute(ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_LOGIN_ERROR));
            }
        } catch (ServiceException e) {
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            log.error("Login check error", e);
        }
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
