package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.manager.ConfigurationManager;
import com.kuntsevich.testsys.entity.Result;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowAllResults implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*String page = ConfigurationManager.getProperty("");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        List<Test> tests;
        try {
            tests = serviceFactory.getTestService().findAll();
            request.setAttribute("results", tests);
            page = ConfigurationManager.getProperty();
        } catch (ServiceException e) {
            log.error("Service can't execute findAll method");
            request.setAttribute(ERROR_MESSAGE, MessageManager.getProperty(SHOW_ALL_TESTS_SERVER_ERROR));
        }
        request.setAttribute(TEMPLATE_PATH, ConfigurationManager.getProperty(SHOW_ALL_TESTS_TEMPLATE_PATH));
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);*/
        String page = ConfigurationManager.getProperty("show_all_results.error");
        ServiceFactory serviceFactory = ServiceFactory.getInstance();

        List<Result> results;

    }
}
