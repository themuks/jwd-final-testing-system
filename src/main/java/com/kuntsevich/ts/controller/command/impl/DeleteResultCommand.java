package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.CommandPath;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.ParameterName;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.Role;
import com.kuntsevich.ts.model.service.ResultService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteResultCommand implements Command {
    private static final Logger log = Logger.getLogger(DeleteResultCommand.class);
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";
    private static final String USER_ID = "&userId=";
    private static final String TUTOR = "Тьютор";
    private static final String ADMIN = "Администратор";
    private static final String MESSAGE_RESULT_DELETE_SUCCESS = "message.result_delete.success";
    private static final String MESSAGE_RESULT_ERROR = "message.result_delete.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter(ParameterName.USER_ID);
        String resultId = request.getParameter(ParameterName.RESULT_ID);
        if (userId == null || userId.isEmpty() || resultId == null || resultId.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(CommandPath.SHOW_USERS);
        }
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute(ParameterName.ROLE);
        ResultService resultService = ServiceFactory.getInstance().getResultService();
        try {
            Role userRole = resultService.findResultUserRole(resultId);
            if (TUTOR.equals(role) && ADMIN.equals(userRole.getName())) {
                request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_RESULT_ERROR));
                return new Router(CommandPath.SHOW_USERS);
            } else {
                if (!resultService.deleteUserResult(resultId)) {
                    request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
                    return new Router(CommandPath.SHOW_USER_RESULTS + USER_ID + userId);
                }
                request.setAttribute(AttributeName.INFO_MESSAGE, MessageManager.getProperty(MESSAGE_RESULT_DELETE_SUCCESS));
                return new Router(CommandPath.SHOW_USER_RESULTS + USER_ID + userId);
            }
        } catch (ServiceException e) {
            log.error(e);
            return new Router(PagePath.ERROR_500);
        }
    }
}
