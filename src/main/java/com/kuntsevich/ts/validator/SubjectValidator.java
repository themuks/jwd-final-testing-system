package com.kuntsevich.ts.validator;

import java.util.regex.Pattern;

public class SubjectValidator extends EntityValidator {
    private static final String NAME_REGEX = ".{0,255}";
    private static final String DESCRIPTION_REGEX = ".{0,16777215}";

    public boolean isNameValid(String name) {
        return Pattern.matches(NAME_REGEX, name);
    }

    public boolean isDescriptionValid(String description) {
        return Pattern.matches(DESCRIPTION_REGEX, description);
    }
}
