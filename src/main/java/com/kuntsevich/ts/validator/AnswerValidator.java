package com.kuntsevich.ts.validator;

public class AnswerValidator extends EntityValidator {
    private static final int TEXT_MIN_LENGTH = 0;
    private static final int TEXT_MAX_LENGTH = 256;

    public static boolean isTextValid(String text) {
        return TEXT_MIN_LENGTH < text.length() && text.length() < TEXT_MAX_LENGTH;
    }
}
