package com.kuntsevich.ts.model.service.impl;

import com.kuntsevich.ts.entity.Subject;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.SubjectDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;
import com.kuntsevich.ts.model.service.SubjectService;
import com.kuntsevich.ts.model.service.exception.ServiceException;

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
