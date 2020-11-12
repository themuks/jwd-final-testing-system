package com.kuntsevich.ts.model.service;

import com.kuntsevich.ts.entity.Subject;
import com.kuntsevich.ts.model.service.exception.ServiceException;

import java.util.List;

public interface SubjectService {
    List<Subject> findAllSubjects() throws ServiceException;

    boolean addSubject(String subjectName, String subjectDescription) throws ServiceException;

    boolean deleteSubject(String subjectId) throws ServiceException;
}
