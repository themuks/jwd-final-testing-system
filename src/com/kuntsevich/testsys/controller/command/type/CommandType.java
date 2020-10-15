package com.kuntsevich.testsys.controller.command.type;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.command.impl.*;

public enum CommandType {
    LOGIN(new LoginCommand()),
    REGISTRATION(new RegistrationCommand()),
    SHOW_ALL_TESTS(new ShowAllTestsCommand()),
    SHOW_AVAILABLE_TESTS(new ShowAvailableTestsCommand()),
    SHOW_TEST(new ShowTestCommand()),
    SUBMIT_TEST(new SubmitTestCommand()),
    SHOW_LOGIN_PAGE(new ShowLoginPageCommand()),
    SHOW_REGISTRATION_PAGE(new ShowRegistrationPageCommand()),
    SHOW_WELCOME_PAGE(new ShowWelcomePageCommand());

    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
