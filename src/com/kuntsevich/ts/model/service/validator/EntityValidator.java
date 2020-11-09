package com.kuntsevich.ts.model.service.validator;

import java.util.regex.Pattern;

public class EntityValidator {
    private static final String ID_REGEX = "[1-9]\\d*";

    public boolean isIdValid(String id) {
        return Pattern.matches(ID_REGEX, id);
    }
}
