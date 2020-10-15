package com.kuntsevich.testsys.model.service.impl;

import com.kuntsevich.testsys.entity.Role;
import com.kuntsevich.testsys.entity.Status;
import com.kuntsevich.testsys.entity.User;
import com.kuntsevich.testsys.model.dao.exception.DaoException;
import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.dao.RoleDao;
import com.kuntsevich.testsys.model.dao.StatusDao;
import com.kuntsevich.testsys.model.dao.UserDao;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;
import com.kuntsevich.testsys.model.service.UserService;
import com.kuntsevich.testsys.model.service.validator.UserValidator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class UserServiceImpl implements UserService {
    private static final String EMPTY_STRING = "";
    private static final String STUDENT_ROLE = "Студент";
    private static final String NAME = "name";
    private static final String STATUS_ACTIVE = "Активный";
    private static final int FIRST_ELEMENT_INDEX = 0;
    private static final String HEX_FORMAT_STRING = "%02x";
    private static final String MESSAGE_DIGEST_MD5 = "MD5";
    private static final String EMAIL_HASH = "email_hash";
    private static final String PASSWORD_HASH = "password_hash";

    @Override
    public Optional<User> checkLogin(String login, String password) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        Map<String, String> criteria = new HashMap<>();
        try {
            criteria.put(EMAIL_HASH, calculateMdHash(calculateMdHash(login)));
            criteria.put(PASSWORD_HASH, calculateMdHash(calculateMdHash(password)));
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Can't find MD5 algorithm", e);
        }
        List<User> users;
        try {
            users = userDao.findByCriteria(criteria);
        } catch (DaoException e) {
            throw new ServiceException("User dao find error", e);
        }
        if (users.size() > 0) {
            User user = users.get(FIRST_ELEMENT_INDEX);
            Random random = new Random();
            int i = random.nextInt();
            String mdHash;
            try {
                mdHash = calculateMdHash(Integer.toString(i));
            } catch (NoSuchAlgorithmException e) {
                throw new ServiceException("Can't find MD5 algorithm", e);
            }
            user.setUserHash(mdHash);
            try {
                userDao.update(user);
            } catch (DaoException e) {
                throw new ServiceException("User dao update error", e);
            }
            optionalUser = Optional.of(user);
        }
        return optionalUser;
    }

    @Override
    public boolean register(String username, String name, String surname, String email, String password) throws ServiceException {
        if (username == null
                || name == null
                || surname == null
                || email == null
                || password == null) {
            throw new ServiceException("Parameters are null");
        }
        UserValidator userValidator = new UserValidator();
        if (!userValidator.isUsernameValid(username)
                || !userValidator.isNameValid(name)
                || !userValidator.isSurnameValid(surname)
                || !userValidator.isEmailValid(email)
                || !userValidator.isPasswordValid(password)) {
            throw new ServiceException("Parameters are incorrect");
        }
        String emailHash;
        String passwordHash;
        try {
            passwordHash = calculateMdHash(calculateMdHash(password));
            emailHash = calculateMdHash(calculateMdHash(email));
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Can't find MD5 algorithm", e);
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        HashMap<String, String> criteria = new HashMap<>();
        criteria.put(EMAIL_HASH, emailHash);
        criteria.put(PASSWORD_HASH, passwordHash);
        List<User> users;
        try {
            users = userDao.findByCriteria(criteria);
        } catch (DaoException e) {
            throw new ServiceException("User dao find error", e);
        }
        if (users.size() > 0) {
            return false;
        }
        User user;
        try {
            user = createUser(username, name, surname, emailHash, passwordHash);
        } catch (DaoException e) {
            throw new ServiceException("Can't create user", e);
        }
        try {
            userDao.add(user);
        } catch (DaoException e) {
            throw new ServiceException("User dao add error", e);
        }
        return true;
    }

    private String calculateMdHash(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(MESSAGE_DIGEST_MD5);
        byte[] hashInBytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashInBytes) {
            stringBuilder.append(String.format(HEX_FORMAT_STRING, b));
        }
        return stringBuilder.toString();
    }

    private User createUser(String username, String name, String surname, String emailHash, String passwordHash) throws DaoException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        RoleDao roleDao = daoFactory.getRoleDao();
        HashMap<String, String> criteria = new HashMap<>();
        criteria.put(NAME, STUDENT_ROLE);
        List<Role> roles = roleDao.findByCriteria(criteria);
        Role roleFromDb = roles.get(FIRST_ELEMENT_INDEX);
        StatusDao statusDao = daoFactory.getStatusDao();
        criteria.clear();
        criteria.put(NAME, STATUS_ACTIVE);
        List<Status> statuses = statusDao.findByCriteria(criteria);
        Status statusFromDb = statuses.get(FIRST_ELEMENT_INDEX);
        User user = new User(username, name, surname, emailHash, passwordHash, EMPTY_STRING, roleFromDb, statusFromDb);
        return user;
    }
}
