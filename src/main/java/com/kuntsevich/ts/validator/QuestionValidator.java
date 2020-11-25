package com.kuntsevich.ts.validator;

import java.util.regex.Pattern;

public class QuestionValidator extends EntityValidator {
    private static final String TEXT_REGEX = ".{1,65535}";
    private static final String POINTS_REGEX = "[1-9]\\d*";
    private static final int TEXT_MIN_LENGTH = 0;
    private static final int TEXT_MAX_LENGTH = 65536;

    public static boolean isTextValid(String text) {
        return TEXT_MIN_LENGTH < text.length() && text.length() < TEXT_MAX_LENGTH;
    }

    public static boolean isPointsValid(String points) {
        return Pattern.matches(POINTS_REGEX, points);
    }
}
