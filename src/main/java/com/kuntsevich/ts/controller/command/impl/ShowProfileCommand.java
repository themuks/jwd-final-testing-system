package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.AttributeName;
import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.ParameterName;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.User;
import com.kuntsevich.ts.model.service.UserService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowProfileCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowProfileCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(AttributeName.USER_ID);
        if (userId == null) {
            return new Router(PagePath.LOGIN).setRedirect();
        }
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            User user = userService.findUserById(userId.toString());
            request.setAttribute(ParameterName.USER, user);
            return new Router(PagePath.PROFILE);
        } catch (ServiceException e) {
            log.error(e);
            return new Router(PagePath.ERROR_500);
        }
    }
}
