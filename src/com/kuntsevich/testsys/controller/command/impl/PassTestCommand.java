package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.exception.ServiceException;
import com.kuntsevich.testsys.model.service.TestService;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;
import com.kuntsevich.testsys.resourse.ConfigurationManager;
import com.kuntsevich.testsys.resourse.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PassTestCommand implements Command {
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String TEST_PAGE_SUCCESS = "path.test.page.success";
    private static final String ID_PARAM = "id";
    private static final String TEST_PAGE_ERROR = "path.test.page.error";
    private static final String TEST_NOTFOUND_ERROR = "message.test.notfound.error";
    private static final String TEST_ATTRIBUTE = "test";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = ConfigurationManager.getProperty(TEST_PAGE_SUCCESS);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        TestService testService = serviceFactory.getTestService();
        long testId;
        try {
            testId = Long.parseLong(request.getParameter(ID_PARAM));
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
                request.setAttribute(ERROR_MESSAGE, MessageManager.getProperty(TEST_NOTFOUND_ERROR));
            }
        } catch (NumberFormatException e) {
            request.setAttribute(ERROR_MESSAGE, MessageManager.getProperty(TEST_NOTFOUND_ERROR));
        }
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
