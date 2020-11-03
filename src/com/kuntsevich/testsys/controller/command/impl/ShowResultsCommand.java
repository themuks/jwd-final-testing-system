package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.PagePath;
import com.kuntsevich.testsys.controller.RequestParameter;
import com.kuntsevich.testsys.controller.Router;
import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.manager.MessageManager;
import com.kuntsevich.testsys.entity.Result;
import com.kuntsevich.testsys.model.service.ResultService;
import com.kuntsevich.testsys.model.service.TestService;
import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;

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
        Long userId = (Long) session.getAttribute(RequestParameter.USER_ID);
        String userHash = (String) session.getAttribute(RequestParameter.USER_HASH);
        if (userId == null || userHash == null || userHash.isEmpty()) {
            page = PagePath.LOGIN;
            return new Router(page, Router.Transition.REDIRECT);
        } else {
            // TODO: 03.11.2020 Check if user have rights to do this
            try {
                List<Result> results = resultService.findCurrentUserResults(userId.toString());
                request.setAttribute(RequestParameter.RESULTS, results);
                request.setAttribute(RequestParameter.TEMPLATE_PATH, PagePath.RESULTS_TEMPLATE);
            } catch (ServiceException e) {
                request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SERVER_ERROR));
            }
        }
        page = PagePath.HOME;
        return new Router(page);
    }
}
