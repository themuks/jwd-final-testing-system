package com.kuntsevich.ts.model.dao;

import com.kuntsevich.ts.entity.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Test dao.
 */
public interface TestDao {
    /**
     * Finds test by id.
     *
     * @param id test id
     * @return optional test
     * @throws DaoException if database error is occurred
     */
    Optional<Test> findById(long id) throws DaoException;

    /**
     * Finds tests by criteria.
     *
     * @param criteria criteria
     * @return tests list
     * @throws DaoException if database error is occurred
     */
    List<Test> findByCriteria(Map<String, String> criteria) throws DaoException;

    /**
     * Finds all tests.
     *
     * @return tests list
     * @throws DaoException if database error is occurred
     */
    List<Test> findAll() throws DaoException;

    /**
     * Adds test.
     *
     * @param test test
     * @return added test id
     * @throws DaoException if database error is occurred
     */
    long add(Test test) throws DaoException;

    /**
     * Updates test.
     *
     * @param test test
     * @throws DaoException if database error is occurred
     */
    void update(Test test) throws DaoException;

    /**
     * Deletes test.
     *
     * @param test test
     * @throws DaoException if database error is occurred
     */
    void delete(Test test) throws DaoException;

    /**
     * Finds tests by subject id.
     *
     * @param subjectId the subject id
     * @return the list
     * @throws DaoException if database error is occurred
     */
    List<Test> findBySubjectId(long subjectId) throws DaoException;

    /**
     * Finds tests with limits.
     *
     * @param offset offset
     * @param limit  limit
     * @return tests list
     * @throws DaoException if database error is occurred
     */
    List<Test> findWithLimits(int offset, int limit) throws DaoException;

    /**
     * Finds tests count.
     *
     * @return tests count
     * @throws DaoException if database error is occurred
     */
    int findCount() throws DaoException;
}
