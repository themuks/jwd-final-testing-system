package com.kuntsevich.ts.controller.command.impl;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.RequestParameter;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.controller.router.Router;
import com.kuntsevich.ts.model.service.TestService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class ShowTestCreateCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        request.setAttribute(RequestParameter.TEMPLATE_PATH, PagePath.TEST_CREATE_TEMPLATE);
        return new Router(PagePath.HOME);
    }
}
