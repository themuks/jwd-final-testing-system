package com.kuntsevich.ts.validator;

import java.util.regex.Pattern;

public class NumberValidator {
    private static final String INTEGER_REGEX = "^-?[0-9]\\d*$";

    public static boolean isIntegerValid(String number) {
        return Pattern.matches(INTEGER_REGEX, number);
    }
}
