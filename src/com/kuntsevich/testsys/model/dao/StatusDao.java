package com.kuntsevich.testsys.model.dao;

import com.kuntsevich.testsys.entity.Status;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StatusDao {
    Optional<Status> findById(long id) throws DaoException;

    List<Status> findByCriteria(Map<String, String> criteria) throws DaoException;

    Optional<Status> findByName(String name) throws DaoException;
}
