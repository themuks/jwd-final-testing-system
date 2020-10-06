package com.kuntsevich.testsys.model.service;

import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.exception.ServiceException;

import java.util.List;

public interface TestService {
    List<Test> findAll() throws ServiceException;
}
