package com.kuntsevich.testsys.resourse;

import java.util.ResourceBundle;

public class MessageManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

    private MessageManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
