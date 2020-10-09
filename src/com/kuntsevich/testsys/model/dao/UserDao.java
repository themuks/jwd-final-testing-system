package com.kuntsevich.testsys.model.dao;

import com.kuntsevich.testsys.entity.User;
import com.kuntsevich.testsys.exception.DaoException;

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
}
