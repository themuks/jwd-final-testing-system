package com.kuntsevich.ts.validator;

public class SubjectValidator extends EntityValidator {
    private static final int NAME_MAX_LENGTH = 256;
    private static final int DESCRIPTION_MAX_LENGTH = 16777216;

    public static boolean isNameValid(String name) {
        return name.length() < NAME_MAX_LENGTH;
    }

    public static boolean isDescriptionValid(String description) {
        return description.length() < DESCRIPTION_MAX_LENGTH;
    }
}
