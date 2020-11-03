package com.kuntsevich.testsys.model.dao;

import com.kuntsevich.testsys.entity.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TestDao {
    Optional<Test> findById(long id) throws DaoException;

    List<Test> findByCriteria(Map<String, String> criteria) throws DaoException;

    List<Test> findAll() throws DaoException;
}
