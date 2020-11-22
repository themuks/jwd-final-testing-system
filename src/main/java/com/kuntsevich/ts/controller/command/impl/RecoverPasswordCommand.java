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

public class RecoverPasswordCommand implements Command {
    private static final Logger log = Logger.getLogger(RecoverPasswordCommand.class);
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";
    private static final String MESSAGE_PASSWORD_RECOVERY_EMAIL = "message.password.recovery_email";
    private static final String MESSAGE_PASSWORD_RECOVERY_ERROR = "message.password.recovery_error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter(ParameterName.EMAIL);
        if (email == null || email.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(PagePath.HOME);
        }
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            if (!userService.sendPasswordRecoveryEmail(email)) {
                request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PASSWORD_RECOVERY_ERROR));
                return new Router(PagePath.PASSWORD_RECOVERY);
            }
        } catch (ServiceException e) {
            log.error(e);
            return new Router(PagePath.ERROR_500);
        }
        request.setAttribute(AttributeName.INFO_MESSAGE, MessageManager.getProperty(MESSAGE_PASSWORD_RECOVERY_EMAIL));
        return new Router(PagePath.HOME);
    }
}
