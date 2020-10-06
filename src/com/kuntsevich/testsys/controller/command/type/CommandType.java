package com.kuntsevich.testsys.controller.command.type;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.command.impl.GoToWelcomePageCommand;
import com.kuntsevich.testsys.controller.command.impl.LoginCommand;
import com.kuntsevich.testsys.controller.command.impl.RegistrationCommand;
import com.kuntsevich.testsys.controller.command.impl.TestFindAllCommand;

public enum CommandType {
    LOGIN(new LoginCommand()),
    REGISTRATION(new RegistrationCommand()),
    TEST_FIND_ALL(new TestFindAllCommand()),
    GO_TO_WELCOME_PAGE(new GoToWelcomePageCommand());

    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
