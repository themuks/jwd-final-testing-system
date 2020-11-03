package com.kuntsevich.testsys.model.dao;

import com.kuntsevich.testsys.entity.Subject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SubjectDao {
    Optional<Subject> findById(long id) throws DaoException;

    List<Subject> findByCriteria(Map<String, String> criteria) throws DaoException;

    List<Subject> findAll() throws DaoException;
}
