package com.kuntsevich.ts.model.dao;

import com.kuntsevich.ts.entity.Subject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SubjectDao {
    Optional<Subject> findById(long id) throws DaoException;

    List<Subject> findByCriteria(Map<String, String> criteria) throws DaoException;

    List<Subject> findAll() throws DaoException;

    Optional<Subject> findByName(String name) throws DaoException;

    long add(Subject subject) throws DaoException;

    void update(Subject subject) throws DaoException;

    void delete(Subject subject) throws DaoException;
}
