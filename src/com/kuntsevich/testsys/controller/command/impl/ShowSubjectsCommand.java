package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.PagePath;
import com.kuntsevich.testsys.controller.RequestParameter;
import com.kuntsevich.testsys.controller.Router;
import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.manager.MessageManager;
import com.kuntsevich.testsys.entity.Result;
import com.kuntsevich.testsys.entity.Subject;
import com.kuntsevich.testsys.model.service.ResultService;
import com.kuntsevich.testsys.model.service.SubjectService;
import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowSubjectsCommand implements Command {
    private static final String MESSAGE_SERVER_ERROR = "message.server.error";

    @Override
    public Router execute(HttpServletRequest request) {
        String page;
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SubjectService subjectService = serviceFactory.getSubjectService();
        try {
            List<Subject> subjects = subjectService.findAllSubjects();
            request.setAttribute(RequestParameter.SUBJECTS, subjects);
            request.setAttribute(RequestParameter.TEMPLATE_PATH, PagePath.SUBJECTS_TEMPLATE);
        } catch (ServiceException e) {
            request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SERVER_ERROR));
        }
        page = PagePath.HOME;
        return new Router(page);
    }
}
