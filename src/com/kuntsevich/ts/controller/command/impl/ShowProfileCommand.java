package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.RequestParameter;
import com.kuntsevich.ts.controller.SessionAttribute;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.entity.User;
import com.kuntsevich.ts.model.service.UserService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ShowProfileCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(SessionAttribute.USER_ID);
        if (userId == null) {
            return new Router(PagePath.LOGIN).setRedirect();
        }
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            User user = userService.findUserById(userId.toString());
            request.setAttribute(RequestParameter.USER, user);
            request.setAttribute(RequestParameter.TEMPLATE_PATH, PagePath.PROFILE_TEMPLATE);
            return new Router(PagePath.HOME);
        } catch (ServiceException e) {
            return new Router(PagePath.ERROR_505);
        }
    }
}
