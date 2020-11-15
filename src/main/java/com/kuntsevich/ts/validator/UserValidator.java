package com.kuntsevich.ts.validator;

import java.util.regex.Pattern;

public class UserValidator extends EntityValidator {
    private static final String[] ROLES = {"Администратор", "Тьютор", "Студент"};
    private static final String NAME_REGEX = "(\\p{Alpha}|[а-яА-Я])+";
    private static final String EMAIL_REGEX = "\\p{Alnum}{4,32}@\\p{Alpha}{4,32}\\.\\p{Alpha}{1,8}+";
    private static final String USERNAME_REGEX = "\\p{Alpha}+";
    private static final String PASSWORD_REGEX = "\\p{Alnum}{4,64}";

    public static boolean isUsernameValid(String username) {
        return Pattern.matches(USERNAME_REGEX, username);
    }

    public static boolean isNameValid(String name) {
        return Pattern.matches(NAME_REGEX, name);
    }

    public static boolean isSurnameValid(String surname) {
        return Pattern.matches(NAME_REGEX, surname);
    }

    public static boolean isEmailValid(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean isPasswordValid(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    public static boolean isRoleValid(String role) {
        boolean flag = false;
        for (String currentRole : ROLES) {
            if (Pattern.matches(currentRole, role)) {
                flag = true;
            }
        }
        return flag;
    }
}
