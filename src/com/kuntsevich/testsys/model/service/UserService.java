package com.kuntsevich.testsys.model.service;

import com.kuntsevich.testsys.entity.User;
import com.kuntsevich.testsys.model.service.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    Optional<User> checkLogin(String email, String password) throws ServiceException;

    boolean register(String username, String name, String surname, String email, String password) throws ServiceException;
}
