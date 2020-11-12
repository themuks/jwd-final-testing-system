package com.kuntsevich.ts.model.service;

import com.kuntsevich.ts.entity.Result;
import com.kuntsevich.ts.model.service.exception.ServiceException;

import java.util.List;

public interface ResultService {
    List<Result> findUserResults(String id) throws ServiceException;

    boolean deleteUserResult(String resultId) throws ServiceException;
}
