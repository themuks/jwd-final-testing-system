package com.kuntsevich.ts.model.service;

import com.kuntsevich.ts.entity.Credential;
import com.kuntsevich.ts.entity.User;
import com.kuntsevich.ts.model.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The interface of User service, contains methods for user actions.
 */
public interface UserService {
    /**
     * Checking if user credential is right to log in the user.
     *
     * @param email    user email
     * @param password user password
     * @return optional of user data such user hash and user id
     * @throws ServiceException if {@code email} or {@code password} are null or invalid
     */
    Optional<Credential> checkLogin(String email, String password) throws ServiceException;

    /**
     * Registration of new user account. Accounts registered by this method
     * are needed for activation by link sent to email.
     *
     * @param username user username
     * @param name     user name
     * @param surname  user surname
     * @param email    user email
     * @param password user password
     * @param role     user role
     * @param promo    promo for tutors
     * @return true, if registration is successful, false otherwise
     * @throws ServiceException if parameters are null or invalid
     */
    boolean registration(String username, String name, String surname, String email, String password, String role, String promo) throws ServiceException;

    /**
     * Finds user role by user id.
     *
     * @param id user id
     * @return user role name
     * @throws ServiceException if error is occurred while finding user role
     */
    String findUserRole(String id) throws ServiceException;

    /**
     * Find user by id user.
     *
     * @param id the id
     * @return user
     * @throws ServiceException if {@code id} is null or invalid
     */
    User findUserById(String id) throws ServiceException;

    /**
     * Changes user password.
     *
     * @param userId           user id
     * @param oldPassword      old password
     * @param newPassword      new password
     * @param newPasswordAgain new password again
     * @return true is changing user data is successful, false otherwise
     * @throws ServiceException if parameters are null or invalid
     */
    boolean changeUserPassword(String userId, String oldPassword, String newPassword, String newPasswordAgain) throws ServiceException;

    /**
     * Changes user data.
     *
     * @param userId   user id
     * @param username user username
     * @param name     user name
     * @param surname  user surname
     * @return true if changing data is successful, false otherwise
     * @throws ServiceException if error is occurred while changing user data
     */
    boolean changeUserData(String userId, String username, String name, String surname) throws ServiceException;

    /**
     * Finds user username.
     *
     * @param id user id
     * @return username
     * @throws ServiceException if {@code id} is null or invalid
     */
    String findUserUsername(String id) throws ServiceException;

    /**
     * Finds users page count.
     *
     * @param recordsPerPage records per page
     * @return page count
     * @throws ServiceException if {@code recordsPerPage} is null or invalid. If error occurred while finding page count
     */
    int findPageCount(String recordsPerPage) throws ServiceException;

    /**
     * Finds page users list.
     *
     * @param userId         user id
     * @param role           user role
     * @param page           page number
     * @param recordsPerPage records per page
     * @return user list of current page
     * @throws ServiceException if parameters are null or invalid. If error occurred while finding page users
     */
    List<User> findPageUsers(String userId, String role, String page, String recordsPerPage) throws ServiceException;

    /**
     * Resets user password.
     *
     * @param userId      user id
     * @param newPassword new password
     * @param secretKey   secret key
     * @return true if password reset is successful, false otherwise
     * @throws ServiceException if error occurred while resetting password
     */
    boolean resetPassword(String userId, String newPassword, String secretKey) throws ServiceException;

    /**
     * Sends verification email.
     *
     * @param email user email
     * @param user  user
     * @return true if sending email is successful, false otherwise
     * @throws ServiceException if error occurred while sending verification email
     */
    boolean sendVerificationEmail(String email, User user) throws ServiceException;

    /**
     * Send password recovery email boolean.
     *
     * @param email the email
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean sendPasswordRecoveryEmail(String email) throws ServiceException;

    /**
     * Deactivates account.
     *
     * @param userId user id
     * @return true if account deactivating is successful, false otherwise
     * @throws ServiceException if error occurred while deactivating user account
     */
    boolean deactivateAccount(String userId) throws ServiceException;

    /**
     * Activates account.
     *
     * @param userId    user id
     * @param secretKey secret key
     * @return the boolean
     * @throws ServiceException if error occurred while activating user account
     */
    boolean activateAccount(String userId, String secretKey) throws ServiceException;
}
