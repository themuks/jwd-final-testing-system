package com.kuntsevich.testsys.model.service;

import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.exception.DaoException;
import com.kuntsevich.testsys.exception.ServiceException;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TestService {
    /*Optional<Test> findById(long id) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        daoFactory.getTestDao().findById(id);
    }

    List<Test> find(Map<String, String> criteria) throws ServiceException {
        if (criteria == null) {
            throw new ServiceException("Criteria is null");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
    }

    List<Test> findAll() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
    }

    void add(Test test) throws ServiceException {
        if (test == null) {
            throw new ServiceException("Test is null");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
    }

    void update(Test test, Map<String, String> params) throws ServiceException {
        if (test == null) {
            throw new ServiceException("Test is null");
        }
        if (params == null) {
            throw new ServiceException("Parameters is null");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
    }

    void delete(Test test) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
    }*/

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
