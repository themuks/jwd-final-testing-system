package com.kuntsevich.testsys.model.dao;

import com.kuntsevich.testsys.entity.Status;
import com.kuntsevich.testsys.model.dao.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StatusDao {
    Optional<Status> findById(long id) throws DaoException;

    List<Status> findByCriteria(Map<String, String> criteria) throws DaoException;
}
