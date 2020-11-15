package com.kuntsevich.ts.model.service.impl;

import com.kuntsevich.ts.entity.Subject;
import com.kuntsevich.ts.entity.Test;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.SubjectDao;
import com.kuntsevich.ts.model.dao.TestDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;
import com.kuntsevich.ts.model.service.SubjectService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.validator.EntityValidator;
import com.kuntsevich.ts.validator.SubjectValidator;

import java.util.List;

public class SubjectServiceImpl implements SubjectService {
    @Override
    public List<Subject> findAllSubjects() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        SubjectDao subjectDao = daoFactory.getSubjectDao();
        try {
            return subjectDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Error while finding all subjects", e);
        }
    }

    @Override
    public boolean addSubject(String subjectName, String subjectDescription) throws ServiceException {
        if (subjectName == null || subjectDescription == null) {
            return false;
        }
        if (!SubjectValidator.isNameValid(subjectName) || !SubjectValidator.isDescriptionValid(subjectDescription)) {
            return false;
        }
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        Subject subject = new Subject(subjectName, subjectDescription);
        try {
            subjectDao.add(subject);
        } catch (DaoException e) {
            throw new ServiceException("Error while adding subject", e);
        }
        return true;
    }

    @Override
    public boolean deleteSubject(String subjectId) throws ServiceException {
        if (subjectId == null) {
            return false;
        }
        if (!EntityValidator.isIdValid(subjectId)) {
            return false;
        }
        TestDao testDao = DaoFactory.getInstance().getTestDao();
        try {
            List<Test> tests = testDao.findBySubjectId(Long.parseLong(subjectId));
            if (tests.size() > 0) {
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException("Error while finding tests by subject id");
        }
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        Subject subject = new Subject();
        subject.setSubjectId(Long.parseLong(subjectId));
        try {
            subjectDao.delete(subject);
        } catch (DaoException e) {
            throw new ServiceException("Error while deleting subject", e);
        }
        return true;
    }
}
