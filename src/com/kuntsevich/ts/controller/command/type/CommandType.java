package com.kuntsevich.ts.controller.command.type;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.command.impl.*;
import com.kuntsevich.ts.controller.router.Router;

public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand()),
    SHOW_ALL_TESTS(new ShowAllTestsCommand()),
    SHOW_RESULTS(new ShowResultsCommand()),
    SHOW_SUBJECTS(new ShowSubjectsCommand()),
    SHOW_TEST(new ShowTestCommand()),
    SHOW_TEST_EDIT(new ShowTestEditCommand()),
    SHOW_TEST_CREATE(new ShowTestCreateCommand()),
    SHOW_PROFILE(new ShowProfileCommand()),
    CREATE_TEST(new CreateTestCommand()),
    SUBMIT_TEST(new SubmitTestCommand()),
    CHANGE_USER_PARAMETERS(new ChangeUserParametersCommand()),
    SHOW_WELCOME_PAGE(r -> new Router(PagePath.WELCOME).setRedirect());

    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
