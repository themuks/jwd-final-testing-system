package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.CommandPath;
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

public class PasswordResetCommand implements Command {
    private static final Logger log = Logger.getLogger(PasswordResetCommand.class);
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";
    private static final String MESSAGE_PASSWORDS_NOT_EQUAL = "message.passwords.not-equal";
    private static final String MESSAGE_DATA_CHANGE_SUCCESS = "message.data_change.success";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String secretKey = request.getParameter(ParameterName.SECRET_KEY);
        String userId = request.getParameter(ParameterName.USER_ID);
        if (secretKey == null || secretKey.isEmpty() || userId == null || userId.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(PagePath.HOME);
        }
        String newPassword = request.getParameter(ParameterName.NEW_PASSWORD);
        String newPasswordAgain = request.getParameter(ParameterName.NEW_PASSWORD_AGAIN);
        if (newPassword == null || newPassword.isEmpty() || newPasswordAgain == null || newPasswordAgain.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PASSWORDS_NOT_EQUAL));
            request.setAttribute(AttributeName.SECRET_KEY, secretKey);
            request.setAttribute(AttributeName.USER_ID, userId);
            return new Router(CommandPath.SHOW_PASSWORD_RESET);
        }
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            if (!userService.resetPassword(userId, newPassword, secretKey)) {
                request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
                return new Router(PagePath.PASSWORD_RESET);
            }
        } catch (ServiceException e) {
            log.error(e);
            return new Router(PagePath.ERROR_500);
        }
        request.setAttribute(AttributeName.INFO_MESSAGE, MessageManager.getProperty(MESSAGE_DATA_CHANGE_SUCCESS));
        return new Router(PagePath.HOME);
    }
}
