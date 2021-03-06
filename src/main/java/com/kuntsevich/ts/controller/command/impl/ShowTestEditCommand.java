package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.ParameterName;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.Subject;
import com.kuntsevich.ts.model.service.SubjectService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowTestEditCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowTestEditCommand.class);
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";
    private static final String MESSAGE_SERVER_ERROR = "message.server.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String questionsCount = request.getParameter(ParameterName.QUESTIONS_COUNT);
        if (questionsCount == null || questionsCount.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(PagePath.HOME);
        }
        long count;
        try {
            count = Long.parseLong(questionsCount);
        } catch (NumberFormatException e) {
            log.error(e);
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(PagePath.ERROR_500);
        }
        SubjectService subjectService = ServiceFactory.getInstance().getSubjectService();
        try {
            List<Subject> allSubjects = subjectService.findAllSubjects();
            request.setAttribute(ParameterName.SUBJECTS, allSubjects);
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SERVER_ERROR));
            return new Router(PagePath.ERROR_500);
        }
        request.setAttribute(ParameterName.QUESTIONS_COUNT, count);
        return new Router(PagePath.TEST_EDIT);
    }
}
