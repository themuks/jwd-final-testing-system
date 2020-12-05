package com.kuntsevich.ts.model.dao;

import com.kuntsevich.ts.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface of User dao.
 */
public interface UserDao {
    /**
     * Finds user by id.
     *
     * @param id id
     * @return optional user
     * @throws DaoException if database error is occurred
     */
    Optional<User> findById(long id) throws DaoException;

    /**
     * Find users by criteria.
     *
     * @param criteria criteria
     * @return users list
     * @throws DaoException if database error is occurred
     */
    List<User> findByCriteria(Map<String, String> criteria) throws DaoException;

    /**
     * Finds all users.
     *
     * @return users list
     * @throws DaoException if database error is occurred
     */
    List<User> findAll() throws DaoException;

    /**
     * Adds user.
     *
     * @param user user
     * @return added user id
     * @throws DaoException if database error is occurred
     */
    long add(User user) throws DaoException;

    /**
     * Updates user.
     *
     * @param user the user
     * @throws DaoException if database error is occurred
     */
    void update(User user) throws DaoException;

    /**
     * Deletes user.
     *
     * @param user the user
     * @throws DaoException if database error is occurred
     */
    void delete(User user) throws DaoException;

    /**
     * Finds user by email.
     *
     * @param email user email
     * @return optional user
     * @throws DaoException if database error is occurred
     */
    Optional<User> findByEmail(String email) throws DaoException;

    /**
     * Finds user by email and password hash.
     *
     * @param email        user email
     * @param passwordHash user password hash
     * @return optional user
     * @throws DaoException if database error is occurred
     */
    Optional<User> findByEmailAndPasswordHash(String email, String passwordHash) throws DaoException;

    /**
     * Finds user by user id and password hash.
     *
     * @param userId       user id
     * @param passwordHash user password hash
     * @return optional user
     * @throws DaoException if database error is occurred
     */
    Optional<User> findByUserIdAndPasswordHash(long userId, String passwordHash) throws DaoException;

    /**
     * Finds user by email and user hash.
     *
     * @param userHash user hash
     * @param email    user email
     * @return optional user
     * @throws DaoException if database error is occurred
     */
    Optional<User> findByEmailAndUserHash(String userHash, String email) throws DaoException;

    /**
     * Finds users with limits.
     *
     * @param offset offset
     * @param limit  limit
     * @return users list
     * @throws DaoException if database error is occurred
     */
    List<User> findWithLimits(int offset, int limit) throws DaoException;

    /**
     * Finds users count.
     *
     * @return users count
     * @throws DaoException if database error is occurred
     */
    int findCount() throws DaoException;
}
