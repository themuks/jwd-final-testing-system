package com.kuntsevich.testsys.model.service;

import com.kuntsevich.testsys.entity.Credential;
import com.kuntsevich.testsys.entity.Result;
import com.kuntsevich.testsys.entity.Role;
import com.kuntsevich.testsys.model.service.exception.ServiceException;

import java.security.Provider;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<Credential> checkLogin(String email, String password) throws ServiceException;

    boolean registration(String username, String name, String surname, String email, String password, String role) throws ServiceException;

    boolean authorization(String id, String userHash) throws ServiceException;

    String findUserRole(String id) throws ServiceException;

    List<Result> findUserResults(String role) throws ServiceException;
}
