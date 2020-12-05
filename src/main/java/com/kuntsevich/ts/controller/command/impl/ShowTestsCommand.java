package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.ParameterName;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.Test;
import com.kuntsevich.ts.model.service.TestService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowTestsCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowTestsCommand.class);
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";
    private static final int RECORDS_PER_PAGE = 5;
    private static final String FIRST_PAGE = "1";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter(ParameterName.PAGE);
        if (page == null || page.isEmpty()) {
            page = FIRST_PAGE;
        }
        try {
            String recordsPerPageStr = Integer.toString(RECORDS_PER_PAGE);
            TestService testService = ServiceFactory.getInstance().getTestService();
            int pageCount = testService.findPageCount(recordsPerPageStr);
            List<Test> tests = testService.findPageTests(page, recordsPerPageStr);
            request.setAttribute(ParameterName.TESTS, tests);
            request.setAttribute(AttributeName.PAGE_COUNT, pageCount);
            request.setAttribute(AttributeName.PAGE, page);
            return new Router(PagePath.TESTS);
        } catch (ServiceException e) {
            log.error("Service can't execute findAll method", e);
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(PagePath.ERROR_500);
        }
    }
}
