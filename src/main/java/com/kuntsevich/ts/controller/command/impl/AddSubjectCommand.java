package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.CommandPath;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.ParameterName;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.model.service.SubjectService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddSubjectCommand implements Command {
    private static final Logger log = Logger.getLogger(AddSubjectCommand.class);
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        MessageManager.setLanguage(language);
        String subjectName = request.getParameter(ParameterName.SUBJECT_NAME);
        String subjectDescription = request.getParameter(ParameterName.SUBJECT_DESCRIPTION);
        if (subjectName == null || subjectName.isEmpty() || subjectDescription == null || subjectDescription.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(PagePath.HOME);
        }
        SubjectService subjectService = ServiceFactory.getInstance().getSubjectService();
        try {
            subjectService.addSubject(subjectName, subjectDescription);
        } catch (ServiceException e) {
            log.error("Error while adding subject", e);
            return new Router(PagePath.ERROR_500);
        }
        return new Router(CommandPath.SHOW_SUBJECTS).setRedirect();
    }
}
