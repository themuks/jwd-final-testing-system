package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.CommandPath;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.ParameterName;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.User;
import com.kuntsevich.ts.model.service.UserService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowUsersCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowUsersCommand.class);
    private static final String MESSAGE_PARAMETERS_ERROR = "message.parameters.error";
    private static final int RECORDS_PER_PAGE = 5;
    private static final String FIRST_PAGE = "1";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(AttributeName.LANGUAGE);
        MessageManager.setLanguage(language);
        String page = request.getParameter(ParameterName.PAGE);
        if (page == null || page.isEmpty()) {
            page = FIRST_PAGE;
        }
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            String recordsPerPageStr = Integer.toString(RECORDS_PER_PAGE);
            int pageCount = userService.findPageCount(recordsPerPageStr);
            List<User> users = userService.findPageUsers(page, recordsPerPageStr);
            request.setAttribute(AttributeName.USERS, users);
            request.setAttribute(AttributeName.PAGE_COUNT, pageCount);
            request.setAttribute(AttributeName.PAGE, page);
            return new Router(PagePath.USERS);
        } catch (ServiceException e) {
            log.error("Error while finding all users", e);
            return new Router(PagePath.ERROR_500);
        }
    }
}
