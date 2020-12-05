package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.ParameterName;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPasswordResetCommand implements Command {
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String secretKey = request.getParameter(ParameterName.SECRET_KEY);
        String userId = request.getParameter(ParameterName.USER_ID);
        if (secretKey == null || secretKey.isEmpty() || userId == null || userId.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(PagePath.HOME);
        }
        request.setAttribute(AttributeName.SECRET_KEY, secretKey);
        request.setAttribute(AttributeName.USER_ID, userId);
        return new Router(PagePath.PASSWORD_RESET);
    }
}
