package com.kuntsevich.testsys.model.service;

import com.kuntsevich.testsys.entity.Result;
import com.kuntsevich.testsys.model.service.exception.ServiceException;

import java.util.List;

public interface ResultService {
    List<Result> findCurrentUserResults(String id) throws ServiceException;
}
