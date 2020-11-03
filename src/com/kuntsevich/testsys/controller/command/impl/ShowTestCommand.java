package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.PagePath;
import com.kuntsevich.testsys.controller.RequestParameter;
import com.kuntsevich.testsys.controller.Router;
import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.manager.ConfigurationManager;
import com.kuntsevich.testsys.controller.manager.MessageManager;
import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.model.service.TestService;
import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;
import com.mysql.cj.log.Log;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowTestCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowTestCommand.class);
    private static final String TEST_NOTFOUND_ERROR = "message.test.notfound.error";
    private static final String SHOW_TEST_TEMPLATE_PATH = "show_test.template_path";

    @Override
    public Router execute(HttpServletRequest request) {
        String page;
        try {
            String testIdParameter = request.getParameter(RequestParameter.ID);
            long testId = Long.parseLong(testIdParameter);
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute(RequestParameter.USER_ID);
            String userHash = (String) session.getAttribute(RequestParameter.USER_HASH);
            if (userId == null || userHash == null || userHash.isEmpty()) {
                session.setAttribute(RequestParameter.ORIGIN, request.getRequestURL().toString());
                page = PagePath.LOGIN;
                return new Router(page, Router.Transition.REDIRECT);
            }
            try {
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                TestService testService = serviceFactory.getTestService();
                List<Test> tests = testService.findAll();
                Test currentTest = null;
                for (Test test : tests) {
                    if (test.getTestId() == testId) {
                        currentTest = test;
                        break;
                    }
                }
                if (currentTest != null) {
                    request.setAttribute(RequestParameter.TEST, currentTest);
                    request.setAttribute(RequestParameter.TEMPLATE_PATH, ConfigurationManager.getProperty(SHOW_TEST_TEMPLATE_PATH));
                }
            } catch (ServiceException e) {
                log.error("Service can't execute tests findAll method", e);
                request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(TEST_NOTFOUND_ERROR));
            }
        } catch (NumberFormatException e) {
            log.error("Error converting testId to long", e);
            request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(TEST_NOTFOUND_ERROR));
        }
        page = PagePath.HOME;
        return new Router(page);
    }
}
