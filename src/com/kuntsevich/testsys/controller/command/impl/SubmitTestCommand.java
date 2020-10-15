package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.manager.ConfigurationManager;
import com.kuntsevich.testsys.controller.manager.MessageManager;
import com.kuntsevich.testsys.model.service.TestService;
import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class SubmitTestCommand implements Command {
    private static final Logger log = Logger.getLogger(SubmitTestCommand.class);
    private static final String PATH_SUBMIT_TEST_SUCCESS = "submit_test.success";
    private static final String TEST_ID = "test-id";
    private static final String PATH_SUBMIT_TEST_ERROR = "path.submit_test.error";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE_SUBMIT_TEST_SUBMIT_ERROR = "message.submit_test.submit.error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = PATH_SUBMIT_TEST_SUCCESS; // TODO: 13.10.2020 Figure out what error page must be
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        TestService testService = serviceFactory.getTestService();
        String testId = request.getParameter(TEST_ID);
        Map<String, String[]> answers = request.getParameterMap();
        try {
            if (testService.submitTest(testId, answers)) {
                page = PATH_SUBMIT_TEST_SUCCESS;
            } else {
                request.setAttribute(ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SUBMIT_TEST_SUBMIT_ERROR));
            }
        } catch (ServiceException e) {
            log.error("Error submitting test", e);
            request.setAttribute(ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SUBMIT_TEST_SUBMIT_ERROR));
        }
        String path = request.getContextPath() + ConfigurationManager.getProperty(page);
        response.sendRedirect(path);
    }
}
