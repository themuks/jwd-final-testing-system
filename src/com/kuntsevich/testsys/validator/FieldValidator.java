package com.kuntsevich.testsys.validator;

public class FieldValidator {
    private static volatile FieldValidator instance;

    private FieldValidator() {
    }

    public FieldValidator getInstance() {
        if (instance == null) {
            synchronized (FieldValidator.class) {
                if (instance == null) {
                    instance = new FieldValidator();
                }
            }
        }
        return instance;
    }
}
