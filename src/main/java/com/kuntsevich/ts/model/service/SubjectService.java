package com.kuntsevich.ts.model.service;

import com.kuntsevich.ts.entity.Subject;
import com.kuntsevich.ts.model.service.exception.ServiceException;

import java.util.List;

public interface SubjectService {
    List<Subject> findAllSubjects() throws ServiceException;
}
