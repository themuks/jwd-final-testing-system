package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.ParameterName;
import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.Result;
import com.kuntsevich.ts.model.service.TestService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class SubmitTestCommand implements Command {
    private static final Logger log = Logger.getLogger(SubmitTestCommand.class);
    private static final String MESSAGE_SUBMIT_TEST_SUBMIT_ERROR = "message.submit_test.submit.error";
    private static final String MESSAGE_SUBMIT_TEST_PARAMETERS_ERROR = "message.submit_test.parameters.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String testId = request.getParameter(ParameterName.TEST_ID);
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(AttributeName.USER_ID);
        Map<String, String[]> answers = request.getParameterMap();
        if (testId == null || testId.isEmpty() || userId == null) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SUBMIT_TEST_PARAMETERS_ERROR));
            return new Router(PagePath.HOME).setRedirect();
        }
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            TestService testService = serviceFactory.getTestService();
            Result result = testService.submitTest(testId, userId.toString(), answers);
            request.setAttribute(AttributeName.RESULT, result);
        } catch (ServiceException e) {
            log.error("Error submitting test", e);
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SUBMIT_TEST_SUBMIT_ERROR));
        }
        return new Router(PagePath.RESULT);
    }
}
