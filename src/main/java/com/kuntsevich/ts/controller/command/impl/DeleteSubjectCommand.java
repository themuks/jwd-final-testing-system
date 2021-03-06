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

public class DeleteSubjectCommand implements Command {
    private static final Logger log = Logger.getLogger(DeleteSubjectCommand.class);
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";
    private static final String MESSAGE_WRONG_ACTION_SUBJECT_DELETE = "message.wrong_action.subject_delete";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String subjectId = request.getParameter(ParameterName.SUBJECT_ID);
        if (subjectId == null || subjectId.isEmpty()) {
            request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_PARAMETERS_ERROR));
            return new Router(CommandPath.SHOW_USERS);
        }
        SubjectService subjectService = ServiceFactory.getInstance().getSubjectService();
        try {
            if (!subjectService.deleteSubject(subjectId)) {
                request.setAttribute(AttributeName.ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_WRONG_ACTION_SUBJECT_DELETE));
                return new Router(CommandPath.SHOW_SUBJECTS);
            }
        } catch (ServiceException e) {
            log.error("Error while deleting subject", e);
            return new Router(PagePath.ERROR_500);
        }
        return new Router(CommandPath.SHOW_SUBJECTS).setRedirect();
    }
}
