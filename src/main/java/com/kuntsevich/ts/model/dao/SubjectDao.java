package com.kuntsevich.ts.model.dao;

import com.kuntsevich.ts.entity.Subject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Subject dao.
 */
public interface SubjectDao {
    /**
     * Finds subject by id.
     *
     * @param id subject id
     * @return optional subject
     * @throws DaoException if database error is occurred
     */
    Optional<Subject> findById(long id) throws DaoException;

    /**
     * Finds subjects by criteria.
     *
     * @param criteria criteria
     * @return subjects list
     * @throws DaoException if database error is occurred
     */
    List<Subject> findByCriteria(Map<String, String> criteria) throws DaoException;

    /**
     * Finds all subjects.
     *
     * @return subjects list
     * @throws DaoException if database error is occurred
     */
    List<Subject> findAll() throws DaoException;

    /**
     * Finds subject by name.
     *
     * @param name subject name
     * @return subject optional
     * @throws DaoException if database error is occurred
     */
    Optional<Subject> findByName(String name) throws DaoException;

    /**
     * Adds subject.
     *
     * @param subject subject
     * @return added subject id
     * @throws DaoException if database error is occurred
     */
    long add(Subject subject) throws DaoException;

    /**
     * Updates subject.
     *
     * @param subject subject
     * @throws DaoException if database error is occurred
     */
    void update(Subject subject) throws DaoException;

    /**
     * Deletes subject.
     *
     * @param subject subject
     * @throws DaoException if database error is occurred
     */
    void delete(Subject subject) throws DaoException;
}
