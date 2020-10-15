package com.kuntsevich.testsys.model.service.validator;

import java.util.regex.Pattern;

public class UserValidator {
    private static final String[] ROLES = {"Admin", "Tutor", "Student"};
    private static final String NAME_REGEX = "(\\p{Alpha}|[а-яА-Я])+";
    private static final String EMAIL_REGEX = "\\p{Alnum}+@\\p{Alpha}+\\.\\p{Alpha}+";
    private static final String USERNAME_REGEX = "\\p{Alpha}+";
    private static final String PASSWORD_REGEX = "\\p{Alnum}{4,64}";

    public boolean isUsernameValid(String username) {
        return Pattern.matches(USERNAME_REGEX, username);
    }

    public boolean isNameValid(String name) {
        return Pattern.matches(NAME_REGEX, name);
    }

    public boolean isSurnameValid(String surname) {
        return Pattern.matches(NAME_REGEX, surname);
    }

    public boolean isEmailValid(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public boolean isPasswordValid(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    public boolean isRoleValid(String role) {
        boolean flag = false;
        for (String currentRole : ROLES) {
            if (Pattern.matches(currentRole, role)) {
                flag = true;
            }
        }
        return flag;
    }
}
