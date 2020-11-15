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
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowUserResultsCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowUserResultsCommand.class);
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";
    private static final int RECORDS_PER_PAGE = 5;
    private static final String FIRST_PAGE = "1";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        MessageManager.setLanguage(language);
        String userId = request.getParameter(ParameterName.USER_ID);
        String page = request.getParameter(ParameterName.PAGE);
        if (userId == null || userId.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(CommandPath.SHOW_USERS);
        }
        if (page == null || page.isEmpty()) {
            page = FIRST_PAGE;
        }
        ResultService resultService = ServiceFactory.getInstance().getResultService();
        try {
            String recordsPerPageStr = Integer.toString(RECORDS_PER_PAGE);
            int pageCount = resultService.findPageCountByUserId(userId, recordsPerPageStr);
            List<Result> results = resultService.findUserPageResults(userId, page, recordsPerPageStr);
            request.setAttribute(AttributeName.USER_ID, userId);
            request.setAttribute(AttributeName.RESULTS, results);
            request.setAttribute(AttributeName.PAGE_COUNT, pageCount);
            request.setAttribute(AttributeName.PAGE, page);
            return new Router(PagePath.RESULTS);
        } catch (ServiceException e) {
            log.error("Error while finding user results", e);
            return new Router(PagePath.ERROR_500);
        }
    }
}
