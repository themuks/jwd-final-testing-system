package com.kuntsevich.ts.model.dao;

import com.kuntsevich.ts.entity.Status;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Status dao.
 */
public interface StatusDao {
    /**
     * Finds status by id.
     *
     * @param id status id
     * @return optional status
     * @throws DaoException if database error is occurred
     */
    Optional<Status> findById(long id) throws DaoException;

    /**
     * Finds statuses by criteria.
     *
     * @param criteria criteria
     * @return statuses list
     * @throws DaoException if database error is occurred
     */
    List<Status> findByCriteria(Map<String, String> criteria) throws DaoException;

    /**
     * Finds status by name.
     *
     * @param name status name
     * @return optional status
     * @throws DaoException if database error is occurred
     */
    Optional<Status> findByName(String name) throws DaoException;
}
