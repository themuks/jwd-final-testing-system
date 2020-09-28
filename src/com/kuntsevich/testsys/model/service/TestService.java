package com.kuntsevich.testsys.model.service;

import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.exception.DaoException;
import com.kuntsevich.testsys.exception.ServiceException;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;

import java.util.List;

public class TestService {
    public List<Test> findAll() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        List<Test> tests;
        try {
            tests = daoFactory.getTestDao().findAll();
        } catch (DaoException e) {
            throw new ServiceException("Error when executing find all method", e);
        }
        return tests;
    }
}
