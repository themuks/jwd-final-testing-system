package com.kuntsevich.testsys.model.dao;

import com.kuntsevich.testsys.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> findById(long id) throws DaoException;

    List<T> find(Map<String, String> criteria) throws DaoException;

    List<T> findAll() throws DaoException;

    void add(T t) throws DaoException;

    void update(T t, Map<String, String> criteria) throws DaoException;

    void delete(T t) throws DaoException;
}
