package com.kuntsevich.testsys.controller.command.type;

import com.kuntsevich.testsys.controller.command.Command;
import com.kuntsevich.testsys.controller.command.impl.*;

public enum CommandType {
    LOGIN(new LoginCommand()),
    REGISTRATION(new RegistrationCommand()),
    SHOW_ALL_TESTS(new ShowAllTestsCommand()),
    SHOW_RESULTS(new ShowResultsCommand()),
    SHOW_SUBJECTS(new ShowSubjectsCommand()),
    SHOW_TEST(new ShowTestCommand()),
    SUBMIT_TEST(new SubmitTestCommand()),
    SHOW_WELCOME_PAGE(new ShowWelcomePageCommand());

    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
