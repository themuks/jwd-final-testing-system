package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.PagePath;
import com.kuntsevich.testsys.controller.RequestParameter;
import com.kuntsevich.testsys.controller.Router;
import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.manager.MessageManager;
import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllTestsCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowAllTestsCommand.class);
    private static final String TESTS = "tests";
    private static final String SHOW_ALL_TESTS_SERVER_ERROR = "show_all_tests.server_error";

    @Override
    public Router execute(HttpServletRequest request) {
        String page;
        List<Test> tests;
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            tests = serviceFactory.getTestService().findAll();
            request.setAttribute(TESTS, tests);
            page = PagePath.HOME;
        } catch (ServiceException e) {
            log.error("Service can't execute findAll method", e);
            request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(SHOW_ALL_TESTS_SERVER_ERROR));
            page = PagePath.ERROR_505;
            return new Router(page, Router.Transition.REDIRECT);
        }
        request.setAttribute(RequestParameter.TEMPLATE_PATH, PagePath.TESTS_TEMPLATE);
        return new Router(page);
    }
}
