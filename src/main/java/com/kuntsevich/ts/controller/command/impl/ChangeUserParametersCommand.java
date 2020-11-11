package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.CommandPath;
import com.kuntsevich.ts.controller.RequestParameter;
import com.kuntsevich.ts.controller.SessionAttribute;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.model.service.UserService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeUserParametersCommand implements Command {
    private static final String MESSAGE_SERVER_ERROR = "message.server.error";
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";
    private static final String MESSAGE_DATA_CHANGE_SUCCESS = "message.data_change.success";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter(RequestParameter.USERNAME);
        String name = request.getParameter(RequestParameter.NAME);
        String surname = request.getParameter(RequestParameter.SURNAME);
        String oldPassword = request.getParameter(RequestParameter.OLD_PASSWORD);
        String newPassword = request.getParameter(RequestParameter.NEW_PASSWORD);
        String newPasswordAgain = request.getParameter(RequestParameter.NEW_PASSWORD_AGAIN);
        if (username == null || username.isEmpty()
                || name == null || name.isEmpty()
                || surname == null || surname.isEmpty()) {
            request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(CommandPath.SHOW_PROFILE);
        }
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID);
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            if (userService.changeUserData(userId.toString(), username, name, surname)) {
                request.setAttribute(RequestParameter.INFO_MESSAGE, MessageManager.getProperty(MESSAGE_DATA_CHANGE_SUCCESS));
            } else {
                request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            }
        } catch (ServiceException e) {
            request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SERVER_ERROR));
            return new Router(CommandPath.SHOW_PROFILE);
        }
        if (!(oldPassword == null) && !oldPassword.isEmpty()
                || !(newPassword == null) && !newPassword.isEmpty()
                || !(newPasswordAgain == null) && !newPasswordAgain.isEmpty()) {
            try {
                if (userService.changeUserPassword(userId.toString(), oldPassword, newPassword, newPasswordAgain)) {
                    request.setAttribute(RequestParameter.INFO_MESSAGE, MessageManager.getProperty(MESSAGE_DATA_CHANGE_SUCCESS));
                } else {
                    request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
                }
            } catch (ServiceException e) {
                request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SERVER_ERROR));
            }
        }
        return new Router("/controller?command=show-profile");
    }
}
