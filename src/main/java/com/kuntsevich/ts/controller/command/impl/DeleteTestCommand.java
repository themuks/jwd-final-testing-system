package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.CommandPath;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.ParameterName;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.model.service.TestService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteTestCommand implements Command {
    private static final Logger log = Logger.getLogger(DeleteTestCommand.class);
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String testId = request.getParameter(ParameterName.TEST_ID);
        if (testId == null || testId.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(PagePath.HOME);
        }
        TestService testService = ServiceFactory.getInstance().getTestService();
        try {
            if (!testService.deleteTest(testId)) {
                request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
                return new Router(CommandPath.SHOW_TESTS);
            }
        } catch (ServiceException e) {
            log.error("Error while adding test", e);
            return new Router(PagePath.ERROR_500);
        }
        return new Router(CommandPath.SHOW_TESTS).setRedirect();
    }
}
