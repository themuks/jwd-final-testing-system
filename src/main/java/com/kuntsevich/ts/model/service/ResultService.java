package com.kuntsevich.ts.model.service;

import com.kuntsevich.ts.entity.Result;
import com.kuntsevich.ts.entity.Role;
import com.kuntsevich.ts.model.service.exception.ServiceException;

import java.util.List;

/**
 * The interface of Result service, contains methods for interacting with results.
 */
public interface ResultService {
    /**
     * Finds all user results list.
     *
     * @param userId user id
     * @return results list
     * @throws ServiceException
     *      if {@code userId} is null or invalid. If error occurred while finding all user results
     */
    List<Result> findAllUserResults(String userId) throws ServiceException;

    /**
     * Deletes user result.
     *
     * @param resultId result id
     * @return true, if deleting user result is successful, false otherwise
     * @throws ServiceException
     *      if error occurred while deleting user result
     */
    boolean deleteUserResult(String resultId) throws ServiceException;

    /**
     * Finds page count by user id.
     *
     * @param userId         user id
     * @param recordsPerPage records per page
     * @return page count
     * @throws ServiceException
     *      if parameters are null or invalid. If error occurred while finding page count by user id
     */
    int findPageCountByUserId(String userId, String recordsPerPage) throws ServiceException;

    /**
     * Finds user page results list.
     *
     * @param userId         user id
     * @param page           page
     * @param recordsPerPage records per page
     * @return results list
     * @throws ServiceException
     *      if parameters are null or invalid. If error occurred while finding user page results
     */
    List<Result> findUserPageResults(String userId, String page, String recordsPerPage) throws ServiceException;

    /**
     * Finds result user role.
     *
     * @param resultId result id
     * @return role
     * @throws ServiceException
     *      if {@code resultId} is null or invalid. If error occurred while finding result user role
     */
    Role findResultUserRole(String resultId) throws ServiceException;
}
