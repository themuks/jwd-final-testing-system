package com.kuntsevich.testsys.model.service;

import com.kuntsevich.testsys.entity.Subject;
import com.kuntsevich.testsys.model.service.exception.ServiceException;

import java.util.List;

public interface SubjectService {
    List<Subject> findAllSubjects() throws ServiceException;
}
