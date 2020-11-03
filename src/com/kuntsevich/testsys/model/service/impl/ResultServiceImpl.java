package com.kuntsevich.testsys.model.service.impl;

import com.kuntsevich.testsys.entity.Result;
import com.kuntsevich.testsys.model.dao.DaoException;
import com.kuntsevich.testsys.model.dao.ResultDao;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;
import com.kuntsevich.testsys.model.service.ResultService;
import com.kuntsevich.testsys.model.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class ResultServiceImpl implements ResultService {
    @Override
    public List<Result> findCurrentUserResults(String id) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        ResultDao resultDao = daoFactory.getResultDao();
        long userId = Long.parseLong(id);
        List<Result> results;
        try {
            results = resultDao.findByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException("Error while finding results by user id", e);
        }
        return results;
    }
}
