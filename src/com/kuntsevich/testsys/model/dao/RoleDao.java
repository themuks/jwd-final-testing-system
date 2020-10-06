package com.kuntsevich.testsys.model.dao;

import com.kuntsevich.testsys.entity.Role;
import com.kuntsevich.testsys.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoleDao {
    Optional<Role> findById(long id) throws DaoException;

    List<Role> findByCriteria(Map<String, String> criteria) throws DaoException;
}
