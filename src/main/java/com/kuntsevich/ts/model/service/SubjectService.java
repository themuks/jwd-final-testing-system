package com.kuntsevich.ts.model.service;

import com.kuntsevich.ts.entity.Subject;
import com.kuntsevich.ts.model.service.exception.ServiceException;

import java.util.List;

/**
 * The interface of Subject service, contains methods for interacting with subjects.
 */
public interface SubjectService {
    /**
     * Finds all subjects list.
     *
     * @return subjects list
     * @throws ServiceException
     *      if error occurred while finding all subjects
     */
    List<Subject> findAllSubjects() throws ServiceException;

    /**
     * Adds subject.
     *
     * @param subjectName        subject name
     * @param subjectDescription subject description
     * @return true, if adding subject is successful, false otherwise
     * @throws ServiceException
     *      if error occurred while adding subject
     */
    boolean addSubject(String subjectName, String subjectDescription) throws ServiceException;

    /**
     * Deletes subject.
     *
     * @param subjectId subject id
     * @return true is deleting subject is successful, false otherwise
     * @throws ServiceException
     *      if error occurred while deleting subject
     */
    boolean deleteSubject(String subjectId) throws ServiceException;
}
