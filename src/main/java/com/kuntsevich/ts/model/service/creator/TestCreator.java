package com.kuntsevich.ts.model.service.creator;

import com.kuntsevich.ts.entity.Question;
import com.kuntsevich.ts.entity.Status;
import com.kuntsevich.ts.entity.Subject;
import com.kuntsevich.ts.entity.Test;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.StatusDao;
import com.kuntsevich.ts.model.dao.SubjectDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;
import com.kuntsevich.ts.model.service.exception.CreatorException;
import com.kuntsevich.ts.validator.TestValidator;

import java.util.List;
import java.util.Optional;

public class TestCreator {
    public static Test createTest(String title, String subjectName, String description, List<Question> questions, String statusName, String pointsToPass) throws CreatorException {
        if (title == null
                || subjectName == null
                || description == null
                || questions == null
                || pointsToPass == null) {
            throw new CreatorException("Parameters are null");
        }
        if (!TestValidator.isTitleValid(title)
                || !TestValidator.isSubjectNameValid(subjectName)
                || !TestValidator.isDescriptionValid(description)
                || !TestValidator.isPointsToPassValid(pointsToPass)) {
            throw new CreatorException("Invalid parameters");
        }
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        Optional<Subject> optionalSubject;
        try {
            optionalSubject = subjectDao.findByName(subjectName);
        } catch (DaoException e) {
            throw new CreatorException("Error while finding subjectName by name", e);
        }
        Subject subject;
        if (optionalSubject.isPresent()) {
            subject = optionalSubject.get();
        } else {
            throw new CreatorException("Subject not found");
        }
        StatusDao statusDao = DaoFactory.getInstance().getStatusDao();
        Optional<Status> optionalStatus;
        try {
            optionalStatus = statusDao.findByName(statusName);
        } catch (DaoException e) {
            throw new CreatorException("Error while finding status by name", e);
        }
        Status status;
        if (optionalStatus.isPresent()) {
            status = optionalStatus.get();
        } else {
            throw new CreatorException("Status not found");
        }
        try {
            int points = Integer.parseInt(pointsToPass);
            return new Test(title, subject, description, questions, status, points);
        } catch (NumberFormatException e) {
            throw new CreatorException("Error while parsing integer", e);
        }
    }
}
