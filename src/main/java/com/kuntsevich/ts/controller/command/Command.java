package com.kuntsevich.ts.controller.command;

import com.kuntsevich.ts.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command interface. Describes api for interacting with command.
 */
public interface Command {
    /**
     * Executes command and returns Router object that contains url
     * and type of action for controller to perform.
     *
     * @param request  request
     * @param response response
     * @return router
     */
    Router execute(HttpServletRequest request, HttpServletResponse response);
}
