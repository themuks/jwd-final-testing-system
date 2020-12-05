package com.kuntsevich.ts.model.dao;

import com.kuntsevich.ts.entity.Role;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Role dao.
 */
public interface RoleDao {
    /**
     * Finds role by id.
     *
     * @param id role id
     * @return optional role
     * @throws DaoException if database error is occurred
     */
    Optional<Role> findById(long id) throws DaoException;

    /**
     * Finds roles by criteria.
     *
     * @param criteria criteria
     * @return roles list
     * @throws DaoException if database error is occurred
     */
    List<Role> findByCriteria(Map<String, String> criteria) throws DaoException;

    /**
     * Finds role by name.
     *
     * @param name role name
     * @return optional role
     * @throws DaoException if database error is occurred
     */
    Optional<Role> findByName(String name) throws DaoException;
}
