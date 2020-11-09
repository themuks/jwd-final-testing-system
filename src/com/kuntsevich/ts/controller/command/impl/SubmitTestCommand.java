package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.RequestParameter;
import com.kuntsevich.ts.controller.SessionAttribute;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.Result;
import com.kuntsevich.ts.model.service.TestService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class SubmitTestCommand implements Command {
    private static final Logger log = Logger.getLogger(SubmitTestCommand.class);
    private static final String MESSAGE_SUBMIT_TEST_SUBMIT_ERROR = "message.submit_test.submit.error";
    private static final String MESSAGE_SUBMIT_TEST_PARAMETERS_ERROR = "message.submit_test.parameters.error";

    @Override
    public Router execute(HttpServletRequest request) {
        String page;
        String testId = request.getParameter(RequestParameter.TEST_ID);
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID);
        Map<String, String[]> answers = request.getParameterMap();
        page = PagePath.HOME;
        if (testId == null || testId.isEmpty() || userId == null) {
            request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SUBMIT_TEST_PARAMETERS_ERROR));
            return new Router(page).setRedirect();
        }
        try {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            TestService testService = serviceFactory.getTestService();
            Result result = testService.submitTest(testId, userId.toString(), answers);
            request.setAttribute(SessionAttribute.RESULT, result);
            request.setAttribute(SessionAttribute.TEMPLATE_PATH, PagePath.RESULT_TEMPLATE);
        } catch (ServiceException e) {
            log.error("Error submitting test", e);
            request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SUBMIT_TEST_SUBMIT_ERROR));
        }
        return new Router(page);
    }
}
