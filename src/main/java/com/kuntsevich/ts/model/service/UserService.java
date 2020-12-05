package com.kuntsevich.ts.model.service;

import com.kuntsevich.ts.entity.Credential;
import com.kuntsevich.ts.entity.User;
import com.kuntsevich.ts.model.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<Credential> checkLogin(String email, String password) throws ServiceException;

    boolean registration(String username, String name, String surname, String email, String password, String role, String promo) throws ServiceException;

    String findUserRole(String id) throws ServiceException;

    User findUserById(String id) throws ServiceException;

    boolean changeUserPassword(String userId, String oldPassword, String newPassword, String newPasswordAgain) throws ServiceException;

    boolean changeUserData(String userId, String username, String name, String surname) throws ServiceException;

    String findUserUsername(String id) throws ServiceException;

    int findPageCount(String recordsPerPage) throws ServiceException;

    List<User> findPageUsers(String userId, String role, String page, String recordsPerPage) throws ServiceException;

    boolean resetPassword(String userId, String newPassword, String secretKey) throws ServiceException;

    boolean sendVerificationEmail(String email, User user) throws ServiceException;

    boolean sendPasswordRecoveryEmail(String email) throws ServiceException;

    boolean deactivateAccount(String userId) throws ServiceException;

    boolean activateAccount(String userId, String secretKey) throws ServiceException;
}
