package com.kuntsevich.ts.validator;

import com.kuntsevich.ts.entity.Subject;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.SubjectDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;

import java.util.List;
import java.util.regex.Pattern;

public class TestValidator extends EntityValidator {
    private static final String POINTS_TO_PASS_REGEX = "[1-9]\\d*";
    private static final int TITLE_MIN_LENGTH = 0;
    private static final int TITLE_MAX_LENGTH = 256;
    private static final int DESCRIPTION_MAX_LENGTH = 2048;

    public static boolean isTitleValid(String title) {
        return TITLE_MIN_LENGTH < title.length() && title.length() < TITLE_MAX_LENGTH;
    }

    public static boolean isDescriptionValid(String description) {
        return description.length() < DESCRIPTION_MAX_LENGTH;
    }

    public static boolean isSubjectNameValid(String subject) {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        List<Subject> subjects;
        try {
            subjects = subjectDao.findAll();
        } catch (DaoException e) {
            return false;
        }
        for (var s : subjects) {
            if (s.getName().equals(subject)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPointsToPassValid(String pointsToPass) {
        return Pattern.matches(POINTS_TO_PASS_REGEX, pointsToPass);
    }
}
