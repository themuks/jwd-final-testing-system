package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.manager.ConfigurationManager;
import com.kuntsevich.testsys.controller.manager.MessageManager;
import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowAllTestsCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowAllTestsCommand.class);
    private static final String PAGE_ERROR = "show_all_tests.error";
    private static final String PAGE_SUCCESS = "show_all_tests.success";
    private static final String TESTS = "tests";
    private static final String SHOW_ALL_TESTS_TEMPLATE_PATH = "show_all_tests.template_path";
    private static final String TEMPLATE_PATH = "templatePath";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String SHOW_ALL_TESTS_SERVER_ERROR = "show_all_tests.server_error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = ConfigurationManager.getProperty(PAGE_ERROR);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        List<Test> tests;
        try {
            tests = serviceFactory.getTestService().findAll();
            request.setAttribute(TESTS, tests);
            page = ConfigurationManager.getProperty(PAGE_SUCCESS);
        } catch (ServiceException e) {
            log.error("Service can't execute findAll method");
            request.setAttribute(ERROR_MESSAGE, MessageManager.getProperty(SHOW_ALL_TESTS_SERVER_ERROR));
        }
        request.setAttribute(TEMPLATE_PATH, ConfigurationManager.getProperty(SHOW_ALL_TESTS_TEMPLATE_PATH));
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
