package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.RequestParameter;
import com.kuntsevich.ts.controller.SessionAttribute;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.Subject;
import com.kuntsevich.ts.model.service.SubjectService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowSubjectsCommand implements Command {
    private static final String MESSAGE_SERVER_ERROR = "message.server.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SubjectService subjectService = serviceFactory.getSubjectService();
        try {
            List<Subject> subjects = subjectService.findAllSubjects();
            request.setAttribute(SessionAttribute.SUBJECTS, subjects);
            request.setAttribute(SessionAttribute.TEMPLATE_PATH, PagePath.SUBJECTS_TEMPLATE);
        } catch (ServiceException e) {
            request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SERVER_ERROR));
        }
        page = PagePath.HOME;
        return new Router(page);
    }
}
