package com.kuntsevich.ts.model.service;

import com.kuntsevich.ts.entity.Result;
import com.kuntsevich.ts.model.service.exception.ServiceException;

import java.util.List;

public interface ResultService {
    List<Result> findAllUserResults(String userId) throws ServiceException;

    boolean deleteUserResult(String resultId) throws ServiceException;

    int findPageCountByUserId(String userId, String recordsPerPage) throws ServiceException;

    List<Result> findUserPageResults(String userId, String page, String recordsPerPage) throws ServiceException;
}
