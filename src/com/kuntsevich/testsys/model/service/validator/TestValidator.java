package com.kuntsevich.testsys.model.service.validator;

import java.util.regex.Pattern;

public class TestValidator {

    private static final String TEST_ID_REGEX = "^-?\\d{1,19}$";

    public boolean isTestIdValid(String testId) {
        return Pattern.matches(TEST_ID_REGEX, testId);
    }
}
