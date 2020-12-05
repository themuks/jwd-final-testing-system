package com.kuntsevich.ts.model.service;

import com.kuntsevich.ts.entity.Result;
import com.kuntsevich.ts.entity.Test;
import com.kuntsevich.ts.model.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * The interface of Test service, contains methods for tests with interactions.
 */
public interface TestService {
    /**
     * Finds all tests.
     *
     * @return list of tests
     * @throws ServiceException
     *      if error occurred while finding all tests
     */
    List<Test> findAll() throws ServiceException;

    /**
     * Submits test result made user.
     *
     * @param testId  test id
     * @param userId  user id
     * @param answers user answers
     * @return result
     * @throws ServiceException
     *      if parameters are null or invalid. If error occurred while submitting test
     */
    Result submitTest(String testId, String userId, Map<String, String[]> answers) throws ServiceException;

    /**
     * Creates test.
     *
     * @param title             test title
     * @param subject           test subject
     * @param description       test description
     * @param pointsToPass      test points to pass
     * @param answers           test answers
     * @param answersAttributes test answers attributes
     * @return true, if test creating is successful, false otherwise
     * @throws ServiceException
     *      if error occurred while creating test
     */
    boolean createTest(String title, String subject, String description, String pointsToPass, Map<String, String[]> answers, List<String> answersAttributes) throws ServiceException;

    /**
     * Deletes test.
     *
     * @param testId test id
     * @return true, if test deleting is successful, false otherwise
     * @throws ServiceException
     *      if error occurred while deleting test
     */
    boolean deleteTest(String testId) throws ServiceException;

    /**
     * Finds page count of tests.
     *
     * @param recordsPerPage records per page
     * @return page count
     * @throws ServiceException
     *      if {@code recordsPerPage} is null or invalid. If error occurred while finding page count
     */
    int findPageCount(String recordsPerPage) throws ServiceException;

    /**
     * Finds page tests list.
     *
     * @param page           page
     * @param recordsPerPage records per page
     * @return tests list
     * @throws ServiceException
     *      if parameters are null or invalid. If error occurred while finding page tests
     */
    List<Test> findPageTests(String page, String recordsPerPage) throws ServiceException;
}
