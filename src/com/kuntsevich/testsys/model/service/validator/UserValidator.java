package com.kuntsevich.testsys.model.service.validator;

import java.util.regex.Pattern;

public class UserValidator {
    private static final String[] ROLES = {"Администратор", "Тьютор", "Студент"};
    private static final String NAME_REGEX = "(\\p{Alpha}|[а-яА-Я])+";
    private static final String EMAIL_REGEX = "\\p{Alnum}{4,32}@\\p{Alpha}{4,32}\\.\\p{Alpha}{1,8}+";
    private static final String USERNAME_REGEX = "\\p{Alpha}+";
    private static final String PASSWORD_REGEX = "\\p{Alnum}{4,64}";
    private static final String ID_REGEX = "[1-9]\\d*";

    public boolean isIdValid(String id) {
        return Pattern.matches(ID_REGEX, id);
    }

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
