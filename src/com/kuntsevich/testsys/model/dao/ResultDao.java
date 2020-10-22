package com.kuntsevich.testsys.model.dao;

import com.kuntsevich.testsys.entity.Result;
import com.kuntsevich.testsys.model.dao.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ResultDao {
    Optional<Result> findById(long id) throws DaoException;

    List<Result> findByCriteria(Map<String, String> criteria) throws DaoException;

    List<Result> findAll() throws DaoException;

    long add(Result result) throws DaoException;

    void update(Result result) throws DaoException;

    void delete(Result result) throws DaoException;
}
