package com.kuntsevich.testsys.model.service.impl;

import com.kuntsevich.testsys.entity.Subject;
import com.kuntsevich.testsys.model.dao.DaoException;
import com.kuntsevich.testsys.model.dao.SubjectDao;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;
import com.kuntsevich.testsys.model.service.SubjectService;
import com.kuntsevich.testsys.model.service.exception.ServiceException;

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
}
