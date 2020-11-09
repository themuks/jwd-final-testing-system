package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.RequestParameter;
import com.kuntsevich.ts.controller.SessionAttribute;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.Result;
import com.kuntsevich.ts.model.service.ResultService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowResultsCommand implements Command {
    private static final String MESSAGE_SERVER_ERROR = "message.server.error";

    @Override
    public Router execute(HttpServletRequest request) {
        String page;
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ResultService resultService = serviceFactory.getResultService();
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID);
        String userHash = (String) session.getAttribute(SessionAttribute.USER_HASH);
        if (userId == null || userHash == null || userHash.isEmpty()) {
            page = PagePath.LOGIN;
            return new Router(page).setRedirect();
        } else {
            // TODO: 03.11.2020 Check if user have rights to do this
            try {
                List<Result> results = resultService.findCurrentUserResults(userId.toString());
                request.setAttribute(SessionAttribute.RESULTS, results);
                request.setAttribute(SessionAttribute.TEMPLATE_PATH, PagePath.RESULTS_TEMPLATE);
            } catch (ServiceException e) {
                request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SERVER_ERROR));
            }
        }
        page = PagePath.HOME;
        return new Router(page);
    }
}
