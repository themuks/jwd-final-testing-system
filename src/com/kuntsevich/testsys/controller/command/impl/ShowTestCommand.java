package com.kuntsevich.testsys.controller.command.impl;

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
import java.io.IOException;
import java.util.List;

public class ShowTestCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowTestCommand.class);
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String SUCCESS_PAGE = "show_test.success";
    private static final String ID_PARAM = "id";
    private static final String ERROR_PAGE = "show_test.error";
    private static final String TEST_NOTFOUND_ERROR = "message.test.notfound.error";
    private static final String TEST_ATTRIBUTE = "test";
    private static final String TEMPLATE_PATH = "templatePath";
    private static final String SHOW_TEST_TEMPLATE_PATH = "show_test.template_path";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = ConfigurationManager.getProperty(SUCCESS_PAGE);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        TestService testService = serviceFactory.getTestService();
        try {
            long testId = Long.parseLong(request.getParameter(ID_PARAM));
            try {
                List<Test> tests = testService.findAll();
                Test currentTest = null;
                for (Test test : tests) {
                    if (test.getTestId() == testId) {
                        currentTest = test;
                        break;
                    }
                }
                if (currentTest != null) {
                    request.setAttribute(TEST_ATTRIBUTE, currentTest);
                }
            } catch (ServiceException e) {
                log.error("Service can't execute tests findAll method", e);
                request.setAttribute(ERROR_MESSAGE, MessageManager.getProperty(TEST_NOTFOUND_ERROR));
            }
        } catch (NumberFormatException e) {
            log.error("Error converting testId to long", e);
            request.setAttribute(ERROR_MESSAGE, MessageManager.getProperty(TEST_NOTFOUND_ERROR));
        }
        request.setAttribute(TEMPLATE_PATH, ConfigurationManager.getProperty(SHOW_TEST_TEMPLATE_PATH));
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
