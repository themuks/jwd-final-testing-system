package com.kuntsevich.ts.model.service.impl;

import com.kuntsevich.ts.entity.Result;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.ResultDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;
import com.kuntsevich.ts.model.service.ResultService;
import com.kuntsevich.ts.model.service.exception.ServiceException;

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
