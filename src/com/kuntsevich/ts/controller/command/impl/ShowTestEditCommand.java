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
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowTestEditCommand implements Command {
    private static final String ADMIN = "Администратор";
    private static final String TUTOR = "Тьютор";
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";
    private static final String MESSAGE_SERVER_ERROR = "message.server.error";

    @Override
    public Router execute(HttpServletRequest request) {
        String questionsCount = request.getParameter(RequestParameter.QUESTIONS_COUNT);
        if (questionsCount == null || questionsCount.isEmpty()) {
            request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(PagePath.HOME);
        }
        long count;
        try {
            count = Long.parseLong(questionsCount);
        } catch (NumberFormatException e) {
            request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(PagePath.ERROR_505).setRedirect();
        }
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute(SessionAttribute.ROLE);
        if ((ADMIN.equals(role) || TUTOR.equals(role))) {
            SubjectService subjectService = ServiceFactory.getInstance().getSubjectService();
            try {
                List<Subject> allSubjects = subjectService.findAllSubjects();
                request.setAttribute(RequestParameter.SUBJECTS, allSubjects);
            } catch (ServiceException e) {
                request.setAttribute(RequestParameter.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SERVER_ERROR));
                return new Router(PagePath.ERROR_505).setRedirect();
            }
            request.setAttribute(RequestParameter.QUESTIONS_COUNT, count);
            request.setAttribute(RequestParameter.TEMPLATE_PATH, PagePath.TEST_EDIT_TEMPLATE);
            return new Router(PagePath.HOME);
        }
        return new Router(PagePath.ERROR_403).setRedirect();
    }
}
