package com.kuntsevich.ts.model.dao;

import com.kuntsevich.ts.entity.Result;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Result dao.
 */
public interface ResultDao {
    /**
     * Finds result by id.
     *
     * @param id id
     * @return optional result
     * @throws DaoException if database error is occurred
     */
    Optional<Result> findById(long id) throws DaoException;

    /**
     * Finds results by criteria.
     *
     * @param criteria criteria
     * @return results list
     * @throws DaoException if database error is occurred
     */
    List<Result> findByCriteria(Map<String, String> criteria) throws DaoException;

    /**
     * Finds all results.
     *
     * @return results list
     * @throws DaoException if database error is occurred
     */
    List<Result> findAll() throws DaoException;

    /**
     * Adds result.
     *
     * @param result result
     * @return added result id
     * @throws DaoException if database error is occurred
     */
    long add(Result result) throws DaoException;

    /**
     * Deletes result.
     *
     * @param result result
     * @throws DaoException if database error is occurred
     */
    void delete(Result result) throws DaoException;

    /**
     * Finds results by user id.
     *
     * @param userId user id
     * @return results list
     * @throws DaoException if database error is occurred
     */
    List<Result> findByUserId(long userId) throws DaoException;

    /**
     * Finds results by test id.
     *
     * @param testId test id
     * @return results list
     * @throws DaoException if database error is occurred
     */
    List<Result> findByTestId(long testId) throws DaoException;

    /**
     * Finds results by user id with limits.
     *
     * @param userId user id
     * @param offset offset
     * @param limit  limit
     * @return results list
     * @throws DaoException if database error is occurred
     */
    List<Result> findByUserIdWithLimits(long userId, int offset, int limit) throws DaoException;

    /**
     * Finds result count by user id.
     *
     * @param userId user id
     * @return users count
     * @throws DaoException if database error is occurred
     */
    int findCountByUserId(long userId) throws DaoException;
}
