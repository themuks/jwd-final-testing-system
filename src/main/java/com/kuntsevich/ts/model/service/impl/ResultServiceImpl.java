package com.kuntsevich.ts.model.service.impl;

import com.kuntsevich.ts.entity.Result;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.ResultDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;
import com.kuntsevich.ts.model.service.ResultService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.validator.EntityValidator;
import com.kuntsevich.ts.validator.NumberValidator;

import java.util.List;

public class ResultServiceImpl implements ResultService {
    @Override
    public List<Result> findAllUserResults(String id) throws ServiceException {
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

    @Override
    public boolean deleteUserResult(String resultId) throws ServiceException {
        if (resultId == null) {
            return false;
        }
        if (!EntityValidator.isIdValid(resultId)) {
            return false;
        }
        ResultDao resultDao = DaoFactory.getInstance().getResultDao();
        Result result = new Result();
        result.setResultId(Long.parseLong(resultId));
        try {
            resultDao.delete(result);
        } catch (DaoException e) {
            throw new ServiceException("Error while deleting result", e);
        }
        return true;
    }

    @Override
    public int findPageCountByUserId(String userId, String recordsPerPage) throws ServiceException {
        if (userId == null || recordsPerPage == null) {
            throw new ServiceException("Parameters are null");
        }
        if (!EntityValidator.isIdValid(userId)) {
            throw new ServiceException("Invalid parameters");
        }
        ResultDao resultDao = DaoFactory.getInstance().getResultDao();
        try {
            int recordsCount = resultDao.findCountByUserId(Long.parseLong(userId));
            int recordsPerPageInt = Integer.parseInt(recordsPerPage);
            int pageCount = recordsCount / recordsPerPageInt;
            pageCount = recordsCount % recordsPerPageInt > 0 ? pageCount + 1 : pageCount;
            return pageCount;
        } catch (DaoException e) {
            throw new ServiceException("Error while finding result count by user id", e);
        }
    }

    @Override
    public List<Result> findUserPageResults(String userId, String page, String recordsPerPage) throws ServiceException {
        if (userId == null || page == null) {
            throw new ServiceException("Parameter is null");
        }
        if (!EntityValidator.isIdValid(userId) || !NumberValidator.isIntegerValid(page)) {
            throw new ServiceException("Parameters are invalid");
        }
        ResultDao resultDao = DaoFactory.getInstance().getResultDao();
        int recordsPerPageInt = Integer.parseInt(recordsPerPage);
        try {
            int pageInt = Integer.parseInt(page);
            if (pageInt > 0) {
                int offset = recordsPerPageInt * (pageInt - 1);
                return resultDao.findByUserIdWithLimits(Long.parseLong(userId), offset, recordsPerPageInt);
            } else {
                return List.of();
            }
        } catch (DaoException e) {
            throw new ServiceException("Error while finding results by user id", e);
        }
    }
}
