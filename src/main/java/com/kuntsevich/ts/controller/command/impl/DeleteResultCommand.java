package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.CommandPath;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.ParameterName;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
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

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        MessageManager.setLanguage(language);
        String userId = request.getParameter(ParameterName.USER_ID);
        String resultId = request.getParameter(ParameterName.RESULT_ID);
        if (userId == null || userId.isEmpty() || resultId == null || resultId.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(CommandPath.SHOW_USERS);
        }
        ResultService resultService = ServiceFactory.getInstance().getResultService();
        try {
            if (!resultService.deleteUserResult(resultId)) {
                request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            }
            return new Router(CommandPath.SHOW_USER_RESULTS + USER_ID + userId);
        } catch (ServiceException e) {
            log.error("Error while deleting user result", e);
            return new Router(PagePath.ERROR_500);
        }
    }
}
