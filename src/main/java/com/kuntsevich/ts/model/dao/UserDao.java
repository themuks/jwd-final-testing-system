package com.kuntsevich.ts.model.dao;

import com.kuntsevich.ts.entity.Credential;
import com.kuntsevich.ts.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id) throws DaoException;

    List<User> findByCriteria(Map<String, String> criteria) throws DaoException;

    List<User> findAll() throws DaoException;

    long add(User user) throws DaoException;

    void update(User user) throws DaoException;

    void delete(User user) throws DaoException;

    Optional<User> findByEmailHash(String emailHash) throws DaoException;

    Optional<User> findByEmailHashAndPasswordHash(String emailHash, String passwordHash) throws DaoException;

    boolean isUserIdAndUserHashExist(Credential credential) throws DaoException;

    Optional<User> findByUserIdAndPasswordHash(long userId, String passwordHash) throws DaoException;

    Optional<User> findByEmailHashAndUserHash(String userHash, String emailHash) throws DaoException;
}
