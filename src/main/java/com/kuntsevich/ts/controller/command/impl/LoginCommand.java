package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.ParameterName;
import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.Credential;
import com.kuntsevich.ts.model.service.UserService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);
    private static final String MESSAGE_LOGIN_ERROR = "message.login.error";
    private static final String LOGIN_SERVER_ERROR = "message.login.server.error";
    private static final String USER_HASH = "userHash";
    private static final String EMAIL_HASH = "userEmail";
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        String email = request.getParameter(ParameterName.EMAIL);
        String password = request.getParameter(ParameterName.PASSWORD);
        String[] checkBoxValues = request.getParameterValues(ParameterName.REMEMBER_ME);
        List<String> checkboxes = checkBoxValues != null ? List.of(checkBoxValues) : new ArrayList<>();
        boolean rememberMe = checkboxes.contains(ParameterName.REMEMBER_ME);
        boolean isSuccessful = false;
        HttpSession session = request.getSession();
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(PagePath.LOGIN);
        }
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();
            Optional<Credential> optionalCredential = userService.checkLogin(email, password);
            if (optionalCredential.isPresent()) {
                Credential credential = optionalCredential.get();
                String userRoleName = userService.findUserRole(Long.toString(credential.getUserId()));
                String username = userService.findUserUsername(Long.toString(credential.getUserId()));
                session.setAttribute(AttributeName.USER_ID, credential.getUserId());
                session.setAttribute(AttributeName.ROLE, userRoleName);
                session.setAttribute(AttributeName.USER_NAME, username);
                if (rememberMe) {
                    response.addCookie(new Cookie(USER_HASH, credential.getUserHash()));
                    response.addCookie(new Cookie(EMAIL_HASH, credential.getEmailHash()));
                }
                page = PagePath.HOME;
                isSuccessful = true;
            } else {
                request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_LOGIN_ERROR));
                page = PagePath.LOGIN;
            }
        } catch (ServiceException e) {
            log.error("Login check error", e);
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(LOGIN_SERVER_ERROR));
            page = PagePath.LOGIN;
        }
        Router router;
        if (isSuccessful) {
            String origin = (String) session.getAttribute(ParameterName.ORIGIN);
            if (origin != null) {
                page = origin;
                session.removeAttribute(ParameterName.ORIGIN);
            }
            router = new Router(page).setRedirect();
        } else {
            router = new Router(page);
        }
        return router;
    }
}
