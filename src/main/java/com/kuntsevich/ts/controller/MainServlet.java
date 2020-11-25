package com.kuntsevich.ts.controller;

import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.command.provider.CommandProvider;
import com.kuntsevich.ts.controller.router.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MainServlet extends HttpServlet {
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
        Router router = command.execute(request, response);
        HttpSession session = request.getSession();
        String page = router.getPage();
        session.setAttribute(ParameterName.CURRENT_PAGE, page);
        switch (router.getTransitionType()) {
            case FORWARD -> request.getRequestDispatcher(router.getPage()).forward(request, response);
            case REDIRECT -> response.sendRedirect(request.getContextPath() + router.getPage());
            case INCLUDE -> request.getRequestDispatcher(router.getPage()).include(request, response);
        }
    }
}
