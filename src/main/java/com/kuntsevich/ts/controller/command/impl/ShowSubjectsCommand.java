package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.Subject;
import com.kuntsevich.ts.model.service.SubjectService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import com.mysql.cj.log.Log;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowSubjectsCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowSubjectsCommand.class);
    private static final String MESSAGE_SERVER_ERROR = "message.server.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        SubjectService subjectService = serviceFactory.getSubjectService();
        try {
            List<Subject> subjects = subjectService.findAllSubjects();
            request.setAttribute(AttributeName.SUBJECTS, subjects);
            return new Router(PagePath.SUBJECTS);
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_SERVER_ERROR));
        }
        return new Router(PagePath.HOME);
    }
}
