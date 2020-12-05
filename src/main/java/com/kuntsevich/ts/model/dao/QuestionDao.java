package com.kuntsevich.ts.model.dao;

import com.kuntsevich.ts.entity.Question;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Question dao.
 */
public interface QuestionDao {
    /**
     * Finds question by id.
     *
     * @param id the id
     * @return optional question
     * @throws DaoException if database error is occurred
     */
    Optional<Question> findById(long id) throws DaoException;

    /**
     * Finds questions by criteria.
     *
     * @param criteria criteria
     * @return questions list
     * @throws DaoException if database error is occurred
     */
    List<Question> findByCriteria(Map<String, String> criteria) throws DaoException;

    /**
     * Finds questions by test id.
     *
     * @param id question id
     * @return questions list
     * @throws DaoException if database error is occurred
     */
    List<Question> findByTestId(long id) throws DaoException;

    /**
     * Adds question.
     *
     * @param question question
     * @param testId   test id
     * @return added question id
     * @throws DaoException if database error is occurred
     */
    long add(Question question, long testId) throws DaoException;

    /**
     * Updates question.
     *
     * @param question question
     * @param testId   test id
     * @throws DaoException if database error is occurred
     */
    void update(Question question, long testId) throws DaoException;

    /**
     * Deletes question.
     *
     * @param question question
     * @throws DaoException if database error is occurred
     */
    void delete(Question question) throws DaoException;
}
