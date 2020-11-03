package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.PagePath;
import com.kuntsevich.testsys.controller.RequestParameter;
import com.kuntsevich.testsys.controller.Router;
import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.manager.MessageManager;
import com.kuntsevich.testsys.entity.Credential;
import com.kuntsevich.testsys.model.service.UserService;
import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);
    private static final String MESSAGE_LOGIN_ERROR = "message.login.error";
    private static final String LOGIN_SERVER_ERROR = "message.login.server.error";

    @Override
    public Router execute(HttpServletRequest request) {
        String page;
        String email = request.getParameter(RequestParameter.EMAIL);
        String password = request.getParameter(RequestParameter.PASSWORD);
        String rememberMe = request.getParameter(RequestParameter.REMEMBER_ME);
        boolean isSuccessful = false;
        HttpSession session = request.getSession();
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();
            Optional<Credential> optionalCredential = userService.checkLogin(email, password);
            if (optionalCredential.isPresent()) {
                Credential credential = optionalCredential.get();
                session.setAttribute(RequestParameter.USER_ID, credential.getUserId());
                session.setAttribute(RequestParameter.USER_HASH, credential.getUserHash());
                String userRoleName = userService.findUserRole(Long.toString(credential.getUserId()));
                session.setAttribute(RequestParameter.ROLE, userRoleName);
                page = PagePath.HOME;
                isSuccessful = true;
            } else {
                request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_LOGIN_ERROR));
                page = PagePath.LOGIN;
            }
        } catch (ServiceException e) {
            request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(LOGIN_SERVER_ERROR));
            page = PagePath.LOGIN;
            log.error("Login check error", e);
        }
        Router router;
        if (isSuccessful) {
            String origin = (String) session.getAttribute(RequestParameter.ORIGIN);
            if (origin != null) {
                page = origin;
                session.removeAttribute(RequestParameter.ORIGIN);
            }
            router = new Router(page, Router.Transition.REDIRECT);
        } else {
            router = new Router(page);
        }
        return router;
    }
}
