package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.RequestParameter;
import com.kuntsevich.ts.controller.SessionAttribute;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.Test;
import com.kuntsevich.ts.model.service.TestService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowTestCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowTestCommand.class);
    private static final String TEST_NOTFOUND_ERROR = "message.test.notfound.error";

    @Override
    public Router execute(HttpServletRequest request) {
        String page;
        try {
            String testIdParameter = request.getParameter(RequestParameter.ID);
            long testId = Long.parseLong(testIdParameter);
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID);
            String userHash = (String) session.getAttribute(SessionAttribute.USER_HASH);
            if (userId == null || userHash == null || userHash.isEmpty()) {
                session.setAttribute(SessionAttribute.ORIGIN, request.getRequestURL().toString());
                page = PagePath.LOGIN;
                return new Router(page).setRedirect();
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
                    request.setAttribute(SessionAttribute.TEST, currentTest);
                    request.setAttribute(SessionAttribute.TEMPLATE_PATH, PagePath.TEST_TEMPLATE);
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
