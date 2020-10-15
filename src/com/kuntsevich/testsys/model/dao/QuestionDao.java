package com.kuntsevich.testsys.model.dao;

import com.kuntsevich.testsys.entity.Question;
import com.kuntsevich.testsys.model.dao.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface QuestionDao {
    Optional<Question> findById(long id) throws DaoException;

    List<Question> findByCriteria(Map<String, String> criteria) throws DaoException;
}
