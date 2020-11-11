package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.RequestParameter;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.Test;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAllTestsCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowAllTestsCommand.class);
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        List<Test> tests;
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            tests = serviceFactory.getTestService().findAll();
            request.setAttribute(RequestParameter.TESTS, tests);
            page = PagePath.HOME;
        } catch (ServiceException e) {
            log.error("Service can't execute findAll method", e);
            request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            page = PagePath.ERROR_505;
            return new Router(page).setRedirect();
        }
        request.setAttribute(RequestParameter.TEMPLATE_PATH, PagePath.TESTS_TEMPLATE);
        return new Router(page);
    }
}
