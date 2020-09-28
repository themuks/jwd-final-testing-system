package com.kuntsevich.testsys.controller.command.provider;

import com.kuntsevich.testsys.controller.command.impl.EmptyCommand;
import com.kuntsevich.testsys.controller.command.type.CommandType;
import com.kuntsevich.testsys.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class CommandProvider {

    public static final String COMMAND_PARAMETER_NAME = "command";

    public Command defineCommand(HttpServletRequest request) {
        Command currentCommand = new EmptyCommand();
        String action = request.getParameter(COMMAND_PARAMETER_NAME);
        if (action == null || action.isEmpty()) {
            return currentCommand;
        }
        CommandType commandType;
        try {
            commandType = CommandType.valueOf(action.toUpperCase());
            currentCommand = commandType.getCommand();
        } catch (IllegalArgumentException e) {
            return currentCommand;
        }
        return currentCommand;
    }
}
