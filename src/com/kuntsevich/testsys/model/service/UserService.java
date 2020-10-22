package com.kuntsevich.testsys.model.service;

import com.kuntsevich.testsys.entity.Credential;
import com.kuntsevich.testsys.entity.Role;
import com.kuntsevich.testsys.model.service.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    Optional<Credential> checkLogin(String email, String password) throws ServiceException;

    boolean register(String username, String name, String surname, String email, String password, String role) throws ServiceException;

    Optional<Role> authorization(String id, String userHash) throws ServiceException;
}
