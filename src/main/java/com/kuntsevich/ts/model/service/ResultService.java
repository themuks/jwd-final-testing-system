package com.kuntsevich.ts.model.service;

import com.kuntsevich.ts.entity.Result;
import com.kuntsevich.ts.model.service.exception.ServiceException;

import java.util.List;

public interface ResultService {
    List<Result> findCurrentUserResults(String id) throws ServiceException;
}
