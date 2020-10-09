package com.kuntsevich.testsys.controller.command.type;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.command.impl.*;

public enum CommandType {
    LOGIN(new LoginCommand()),
    REGISTRATION(new RegistrationCommand()),
    TEST_FIND_ALL(new TestFindAllCommand()),
    PASS_TEST(new PassTestCommand()),
    GO_TO_WELCOME_PAGE(new GoToWelcomePageCommand());

    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
