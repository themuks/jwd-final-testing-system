package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.CommandPath;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateTestCommand implements Command {
    private static final Logger log = Logger.getLogger(CreateTestCommand.class);
    private static final String MESSAGE_SERVER_ERROR = "message.server.error";
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String title = request.getParameter(ParameterName.TITLE);
        String description = request.getParameter(ParameterName.DESCRIPTION);
        String subject = request.getParameter(ParameterName.SUBJECT);
        String pointsToPass = request.getParameter(ParameterName.POINTS_TO_PASS);
        Map<String, String[]> answers = request.getParameterMap();
        String[] checkboxesRaw = request.getParameterValues(ParameterName.CHECKBOXES);
        List<String> answersAttributes = checkboxesRaw != null ? List.of(checkboxesRaw) : new ArrayList<>();
        TestService testService = ServiceFactory.getInstance().getTestService();
        try {
            if (!testService.createTest(title, subject, description, pointsToPass, answers, answersAttributes)) {
                request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            }
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SERVER_ERROR));
        }
        return new Router(CommandPath.SHOW_TESTS);
    }
}
