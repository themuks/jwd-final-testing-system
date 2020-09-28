package com.kuntsevich.testsys.controller.command.type;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.command.impl.TestFindAllCommand;

public enum CommandType {
    TEST_FIND_ALL_COMMAND(new TestFindAllCommand());

    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
