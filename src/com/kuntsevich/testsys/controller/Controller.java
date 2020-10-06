package com.kuntsevich.testsys.controller;

import com.kuntsevich.testsys.connection.DatabaseConnectionPool;
import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.command.provider.CommandProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class Controller extends HttpServlet {
    private static final Logger log = Logger.getLogger(Controller.class);
    private static final String UTF_8_ENCODING = "UTF-8";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

    @Override
    public void destroy() {
        DatabaseConnectionPool connectionPool;
        try {
            connectionPool = DatabaseConnectionPool.getInstance();
            connectionPool.destroyPool();
        } catch (SQLException e) {
            log.error("Can't get instance of database connection pool to destroy connection pool", e);
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(UTF_8_ENCODING);
        response.setCharacterEncoding(UTF_8_ENCODING);
        CommandProvider commandProvider = new CommandProvider();
        Command command = commandProvider.defineCommand(request);
        command.execute(request, response);
    }
}
