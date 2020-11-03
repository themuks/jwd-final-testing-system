package com.kuntsevich.testsys.controller;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.command.provider.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandProvider commandProvider = new CommandProvider();
        Command command = commandProvider.defineCommand(request);
        Router router = command.execute(request);
        HttpSession session = request.getSession();
        String page = router.getPage();
        session.setAttribute(RequestParameter.CURRENT_PAGE, page);
        if (router.getTransition() == Router.Transition.FORWARD) {
            request.getRequestDispatcher(page).forward(request, response);
        } else {
            response.sendRedirect(page);
        }
    }
}
