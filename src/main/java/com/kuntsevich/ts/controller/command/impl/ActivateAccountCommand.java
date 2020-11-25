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

public class ActivateAccountCommand implements Command {
    private static final Logger log = Logger.getLogger(ActivateAccountCommand.class);
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";
    private static final String MESSAGE_ACCOUNT_ACTIVATE_SUCCESS = "message.account.activate.success";
    private static final String MESSAGE_ACCOUNT_ACTIVATE_ERROR = "message.account.activate.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter(ParameterName.USER_ID);
        String secretKey = request.getParameter(ParameterName.SECRET_KEY);
        if (userId == null || userId.isEmpty() || secretKey == null || secretKey.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(PagePath.HOME);
        }
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            if (!userService.activateAccount(userId, secretKey)) {
                request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_ACCOUNT_ACTIVATE_ERROR));
                return new Router(PagePath.LOGIN);
            }
            request.setAttribute(AttributeName.INFO_MESSAGE, MessageManager.getProperty(MESSAGE_ACCOUNT_ACTIVATE_SUCCESS));
            return new Router(PagePath.LOGIN);
        } catch (ServiceException e) {
            log.error(e);
            return new Router(PagePath.ERROR_500);
        }
    }
}
