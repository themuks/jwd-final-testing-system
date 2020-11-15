package com.kuntsevich.ts.validator;

import java.util.regex.Pattern;

public class EntityValidator {
    private static final String ID_REGEX = "[1-9]\\d*";

    public static boolean isIdValid(String id) {
        return Pattern.matches(ID_REGEX, id);
    }
}
