package com.kuntsevich.ts.model.dao;

import com.kuntsevich.ts.entity.Question;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface QuestionDao {
    Optional<Question> findById(long id) throws DaoException;

    List<Question> findByCriteria(Map<String, String> criteria) throws DaoException;

    List<Question> findByTestId(long id) throws DaoException;

    long add(Question question, long testId) throws DaoException;

    void update(Question question, long testId) throws DaoException;

    void delete(Question question) throws DaoException;
}
