package com.kuntsevich.testsys.model.dao;

import com.kuntsevich.testsys.entity.Subject;
import com.kuntsevich.testsys.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SubjectDao {
    Optional<Subject> findById(long id) throws DaoException;

    List<Subject> findByCriteria(Map<String, String> criteria) throws DaoException;
}
