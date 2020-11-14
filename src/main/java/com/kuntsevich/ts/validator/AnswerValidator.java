package com.kuntsevich.ts.validator;

import java.util.regex.Pattern;

public class AnswerValidator extends EntityValidator {
    private static final String TEXT_REGEX = ".{1,255}";

    public boolean isTextValid(String text) {
        return Pattern.matches(TEXT_REGEX, text);
    }
}
