package com.kuntsevich.ts.model.dao;

import com.kuntsevich.ts.entity.Answer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AnswerDao {
    Optional<Answer> findById(long id) throws DaoException;

    List<Answer> findByCriteria(Map<String, String> criteria) throws DaoException;

    List<Answer> findByQuestionId(long id) throws DaoException;

    long add(Answer answer, long questionId) throws DaoException;

    void update(Answer answer, long questionId) throws DaoException;

    void delete(Answer answer) throws DaoException;
}
