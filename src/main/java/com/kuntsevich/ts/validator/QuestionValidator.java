package com.kuntsevich.ts.validator;

import java.util.regex.Pattern;

public class QuestionValidator extends EntityValidator {
    private static final String TEXT_REGEX = ".{1,65535}";
    private static final String POINTS_REGEX = "[1-9]\\d*";

    public static boolean isTextValid(String text) {
        return Pattern.matches(TEXT_REGEX, text);
    }

    public static boolean isPointsValid(String points) {
        return Pattern.matches(POINTS_REGEX, points);
    }
}
