package com.kuntsevich.ts.model.dao;

import com.kuntsevich.ts.entity.Answer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Answer dao.
 */
public interface AnswerDao {
    /**
     * Finds answer by id.
     *
     * @param id answer id
     * @return optional answer
     * @throws DaoException if database error is occurred
     */
    Optional<Answer> findById(long id) throws DaoException;

    /**
     * Finds answers by criteria.
     *
     * @param criteria criteria
     * @return answers list
     * @throws DaoException if database error is occurred
     */
    List<Answer> findByCriteria(Map<String, String> criteria) throws DaoException;

    /**
     * Finds answers by question id.
     *
     * @param id answer id
     * @return answers list
     * @throws DaoException if database error is occurred
     */
    List<Answer> findByQuestionId(long id) throws DaoException;

    /**
     * Adds answer.
     *
     * @param answer     answer
     * @param questionId question id
     * @return added answer id
     * @throws DaoException if database error is occurred
     */
    long add(Answer answer, long questionId) throws DaoException;

    /**
     * Updates answer.
     *
     * @param answer     answer
     * @param questionId question id
     * @throws DaoException if database error is occurred
     */
    void update(Answer answer, long questionId) throws DaoException;

    /**
     * Deletes answer.
     *
     * @param answer answer
     * @throws DaoException if database error is occurred
     */
    void delete(Answer answer) throws DaoException;
}
