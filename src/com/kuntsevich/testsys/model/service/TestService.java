package com.kuntsevich.testsys.model.service;

import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.model.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface TestService {
    List<Test> findAll() throws ServiceException;

    boolean submitTest(String testId, Map<String, String[]> answers) throws ServiceException;
}
