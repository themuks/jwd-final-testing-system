package com.kuntsevich.ts.validator;

import com.kuntsevich.ts.entity.Subject;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.SubjectDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;

import java.util.List;
import java.util.regex.Pattern;

public class TestValidator extends EntityValidator {
    private static final String TITLE_REGEX = ".{1,255}";
    private static final String DESCRIPTION_REGEX = ".{0,2048}";
    private static final String POINTS_TO_PASS_REGEX = "[1-9]\\d*";

    public boolean isTitleValid(String title) {
        return Pattern.matches(TITLE_REGEX, title);
    }

    public boolean isDescriptionValid(String description) {
        return Pattern.matches(DESCRIPTION_REGEX, description);
    }

    public boolean isSubjectNameValid(String subject) {
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

    public boolean isPointsToPassValid(String pointsToPass) {
        return Pattern.matches(POINTS_TO_PASS_REGEX, pointsToPass);
    }
}
