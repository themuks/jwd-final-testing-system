package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.exception.ServiceException;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TestFindAllCommand implements Command {
    private static final Logger log = Logger.getLogger(TestFindAllCommand.class);
    private static final String JSP_SUCCESS_PAGE = "/WEB-INF/jsp/main.jsp";
    private static final String JSP_ERROR_PAGE = "/WEB-INF/jsp/error.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = JSP_ERROR_PAGE;
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        List<Test> tests;
        try {
            tests = serviceFactory.getTestService().findAll();
            request.setAttribute("tests", tests);
            page = JSP_SUCCESS_PAGE;
        } catch (ServiceException e) {
            log.error("Service can't execute findAll method");
        }
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
