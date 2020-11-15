package com.kuntsevich.ts.model.dao;

import com.kuntsevich.ts.entity.Test;
import com.kuntsevich.ts.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TestDao {
    Optional<Test> findById(long id) throws DaoException;

    List<Test> findByCriteria(Map<String, String> criteria) throws DaoException;

    List<Test> findAll() throws DaoException;

    long add(Test test) throws DaoException;

    void update(Test test) throws DaoException;

    void delete(Test test) throws DaoException;

    List<Test> findBySubjectId(long subjectId) throws DaoException;

    List<Test> findWithLimits(int offset, int limit) throws DaoException;

    int findCount() throws DaoException;
}
