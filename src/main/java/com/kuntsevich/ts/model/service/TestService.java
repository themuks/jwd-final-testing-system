package com.kuntsevich.ts.model.service;

import com.kuntsevich.ts.entity.Result;
import com.kuntsevich.ts.entity.Test;
import com.kuntsevich.ts.model.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface TestService {
    List<Test> findAll() throws ServiceException;

    Result submitTest(String testId, String userId, Map<String, String[]> answers) throws ServiceException;

    boolean createTest(String title, String subject, String description, String pointsToPass, Map<String, String[]> answers, List<String> answersAttributes) throws ServiceException;

    boolean deleteTest(String testId) throws ServiceException;

    int findPageCount(String recordsPerPage) throws ServiceException;

    List<Test> findPageTests(String page, String recordsPerPage) throws ServiceException;
}
