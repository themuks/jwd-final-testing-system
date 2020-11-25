package com.kuntsevich.ts.validator;

public class EmailValidator extends EntityValidator {
    private static final int SUBJECT_MIN_LENGTH = 0;
    private static final int TEXT_MIN_LENGTH = 0;

    public static boolean isSubjectValid(String subject) {
        return subject.length() > SUBJECT_MIN_LENGTH;
    }

    public static boolean isTextValid(String text) {
        return text.length() > TEXT_MIN_LENGTH;
    }
}
