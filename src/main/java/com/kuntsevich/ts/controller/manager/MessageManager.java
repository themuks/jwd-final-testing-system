package com.kuntsevich.ts.controller.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private static final String BUNDLE_NAME = "content.message";
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

    private MessageManager() {
    }

    public static void setLanguage(String language) {
        if (language != null) {
            Locale locale = new Locale(language);
            resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        }
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
