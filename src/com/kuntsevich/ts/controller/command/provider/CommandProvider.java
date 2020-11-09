package com.kuntsevich.ts.controller.command.provider;

import com.kuntsevich.ts.controller.RequestParameter;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.command.impl.EmptyCommand;
import com.kuntsevich.ts.controller.command.type.CommandType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CommandProvider {
    private static final Logger log = Logger.getLogger(CommandProvider.class);
    private static final String UNDERSCORE = "_";
    private static final String HYPHEN = "-";

    public Command defineCommand(HttpServletRequest request) {
        Command currentCommand = new EmptyCommand();
        String action = request.getParameter(RequestParameter.COMMAND);
        if (action == null || action.isEmpty()) {
            return currentCommand;
        }
        CommandType commandType;
        try {
            commandType = CommandType.valueOf(action.toUpperCase().replaceAll(HYPHEN, UNDERSCORE));
            currentCommand = commandType.getCommand();
        } catch (IllegalArgumentException e) {
            log.warn("Can't recognize command", e);
            return currentCommand;
        }
        return currentCommand;
    }
}
