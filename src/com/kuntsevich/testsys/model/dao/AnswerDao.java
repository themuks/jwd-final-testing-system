package com.kuntsevich.testsys.model.dao;

import com.kuntsevich.testsys.entity.Answer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AnswerDao {
    Optional<Answer> findById(long id) throws DaoException;

    List<Answer> findByCriteria(Map<String, String> criteria) throws DaoException;

    List<Answer> findByQuestionId(long id) throws DaoException;
}
