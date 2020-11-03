package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.PagePath;
import com.kuntsevich.testsys.controller.Router;
import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.manager.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowWelcomePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        String page = PagePath.WELCOME;
        return new Router(page, Router.Transition.REDIRECT);
    }
}
