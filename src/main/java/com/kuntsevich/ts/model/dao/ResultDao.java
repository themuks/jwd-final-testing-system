package com.kuntsevich.ts.model.dao;

import com.kuntsevich.ts.entity.Result;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ResultDao {
    Optional<Result> findById(long id) throws DaoException;

    List<Result> findByCriteria(Map<String, String> criteria) throws DaoException;

    List<Result> findAll() throws DaoException;

    long add(Result result) throws DaoException;

    void delete(Result result) throws DaoException;

    List<Result> findByUserId(long userId) throws DaoException;

    List<Result> findByTestId(long testId) throws DaoException;

    List<Result> findByUserIdWithLimits(long userId, int offset, int limit) throws DaoException;

    int findCountByUserId(long userId) throws DaoException;
}
