package com.kuntsevich.testsys.exception;

public class DatabasePoolException extends Exception {
    public DatabasePoolException() {
        super();
    }

    public DatabasePoolException(String message) {
        super(message);
    }

    public DatabasePoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabasePoolException(Throwable cause) {
        super(cause);
    }
}
