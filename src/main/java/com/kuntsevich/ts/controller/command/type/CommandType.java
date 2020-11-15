package com.kuntsevich.ts.controller.command.type;

import com.kuntsevich.ts.controller.PagePath;
import com.kuntsevich.ts.controller.command.Command;
import com.kuntsevich.ts.controller.command.impl.*;
import com.kuntsevich.ts.controller.router.Router;

public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand()),
    SHOW_TESTS(new ShowTestsCommand()),
    SHOW_SUBJECTS(new ShowSubjectsCommand()),
    SHOW_TEST(new ShowTestCommand()),
    SHOW_TEST_EDIT(new ShowTestEditCommand()),
    SHOW_TEST_CREATE(new ShowTestCreateCommand()),
    SHOW_PROFILE(new ShowProfileCommand()),
    SHOW_SUBJECT_ADD(new ShowSubjectAddCommand()),
    SHOW_USERS(new ShowUsersCommand()),
    SHOW_USER_RESULTS(new ShowUserResultsCommand()),
    DELETE_RESULT(new DeleteResultCommand()),
    ADD_SUBJECT(new AddSubjectCommand()),
    DELETE_SUBJECT(new DeleteSubjectCommand()),
    CREATE_TEST(new CreateTestCommand()),
    DELETE_TEST(new DeleteTestCommand()),
    SUBMIT_TEST(new SubmitTestCommand()),
    CHANGE_USER_PARAMETERS(new ChangeUserParametersCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    SHOW_WELCOME_PAGE((req, resp) -> new Router(PagePath.WELCOME).setRedirect());

    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
