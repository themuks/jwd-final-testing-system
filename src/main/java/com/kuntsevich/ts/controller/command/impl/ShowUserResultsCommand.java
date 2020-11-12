package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.CommandPath;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.ParameterName;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.Result;
import com.kuntsevich.ts.model.service.ResultService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUserResultsCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowUserResultsCommand.class);
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter(ParameterName.USER_ID);
        if (userId == null || userId.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(CommandPath.SHOW_ALL_USERS);
        }
        ResultService resultService = ServiceFactory.getInstance().getResultService();
        try {
            List<Result> results = resultService.findUserResults(userId);
            request.setAttribute(AttributeName.USER_ID, userId);
            request.setAttribute(AttributeName.RESULTS, results);
            request.setAttribute(AttributeName.TEMPLATE_PATH, PagePath.RESULTS_TEMPLATE);
        } catch (ServiceException e) {
            log.error("Error while finding user results", e);
            return new Router(PagePath.ERROR_500).setRedirect();
        }
        return new Router(PagePath.HOME);
    }
}
