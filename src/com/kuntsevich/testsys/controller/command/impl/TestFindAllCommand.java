package com.kuntsevich.testsys.controller.command.impl;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.exception.ServiceException;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class TestFindAllCommand implements Command {
    private static final String JSP_SUCCESS_PAGE = "/jsp/main.jsp";
    private static final String JSP_ERROR_PAGE = "/jsp/error.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        String page = JSP_ERROR_PAGE;
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        List<Test> tests;
        try {
            tests = serviceFactory.getTestService().findAll();
            request.setAttribute("tests", tests);
            page = JSP_SUCCESS_PAGE;
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return page;
    }
}
