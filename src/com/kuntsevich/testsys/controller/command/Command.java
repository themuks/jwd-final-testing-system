package com.kuntsevich.testsys.controller.command;

import com.kuntsevich.testsys.controller.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    Router execute(HttpServletRequest request);
}
