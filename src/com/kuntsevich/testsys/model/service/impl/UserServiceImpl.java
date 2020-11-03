package com.kuntsevich.testsys.model.service.impl;

import com.kuntsevich.testsys.entity.Credential;
import com.kuntsevich.testsys.entity.Result;
import com.kuntsevich.testsys.entity.Role;
import com.kuntsevich.testsys.entity.User;
import com.kuntsevich.testsys.model.dao.UserDao;
import com.kuntsevich.testsys.model.dao.DaoException;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;
import com.kuntsevich.testsys.model.service.UserService;
import com.kuntsevich.testsys.model.service.creator.CredentialCreator;
import com.kuntsevich.testsys.model.service.creator.UserCreator;
import com.kuntsevich.testsys.model.service.exception.CreatorException;
import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.service.validator.UserValidator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class UserServiceImpl implements UserService {
    private static final String HEX_FORMAT_STRING = "%02x";
    private static final String MESSAGE_DIGEST_MD5 = "MD5";
    private static final String SALT = "s7l3T1hTEA3";

    @Override
    public Optional<Credential> checkLogin(String email, String password) throws ServiceException {
        Optional<Credential> optionalCredential = Optional.empty();
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        String emailHash;
        String passwordHash;
        try {
            emailHash = calculateMdHash(email + SALT);
            passwordHash = calculateMdHash(password + SALT);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Can't find MD5 algorithm", e);
        }
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findByEmailHashAndPasswordHash(emailHash, passwordHash);
        } catch (DaoException e) {
            throw new ServiceException("Error finding user by emailHash and passwordHash", e);
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
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
            Credential credential = new Credential(user.getUserId(), user.getUserHash());
            optionalCredential = Optional.of(credential);
        }
        return optionalCredential;
    }

    @Override
    public boolean registration(String username, String name, String surname, String email, String password, String role) throws ServiceException {
        if (username == null
                || name == null
                || surname == null
                || email == null
                || password == null
                || role == null) {
            throw new ServiceException("Parameters are null");
        }
        UserValidator userValidator = new UserValidator();
        if (!userValidator.isUsernameValid(username)
                || !userValidator.isNameValid(name)
                || !userValidator.isSurnameValid(surname)
                || !userValidator.isEmailValid(email)
                || !userValidator.isPasswordValid(password)
                || !userValidator.isRoleValid(role)) {
            throw new ServiceException("Parameters are incorrect");
        }
        String emailHash;
        String passwordHash;
        try {
            passwordHash = calculateMdHash(password + SALT);
            emailHash = calculateMdHash(email + SALT);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Can't find MD5 algorithm", e);
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findByEmailHash(emailHash);
        } catch (DaoException e) {
            throw new ServiceException("Error finding user by emailHash", e);
        }
        if (optionalUser.isPresent()) {
            return false;
        }
        // TODO: 15.10.2020 Send email to confirm registration
        User user;
        try {
            user = UserCreator.createUser(username, name, surname, emailHash, passwordHash, role);
        } catch (CreatorException e) {
            throw new ServiceException("Error creating user", e);
        }
        try {
            userDao.add(user);
        } catch (DaoException e) {
            throw new ServiceException("User dao add error", e);
        }
        return true;
    }

    @Override
    public boolean authorization(String id, String userHash) throws ServiceException {
        if (id == null || userHash == null) {
            throw new ServiceException("Parameters are null");
        }
        UserValidator userValidator = new UserValidator();
        if (!userValidator.isIdValid(id)) {
            throw new ServiceException("Parameters are incorrect");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        Credential credential;
        try {
            credential = CredentialCreator.createCredential(id, userHash);
        } catch (CreatorException e) {
            throw new ServiceException("Error creating credential", e);
        }
        try {
            return userDao.isUserIdAndUserHashExist(credential);
        } catch (DaoException e) {
            throw new ServiceException("Error while checking user id and user hash", e);
        }
    }

    @Override
    public String findUserRole(String id) throws ServiceException {
        if (id == null) {
            throw new ServiceException("Parameters are null");
        }
        UserValidator userValidator = new UserValidator();
        if (!userValidator.isIdValid(id)) {
            throw new ServiceException("Parameters are incorrect");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        long idValue = Long.parseLong(id);
        Optional<User> userOptional;
        try {
            userOptional = userDao.findById(idValue);
        } catch (DaoException e) {
            throw new ServiceException("Error finding user by id", e);
        }
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getRole().getName();
        } else {
            throw new ServiceException("User is not exist");
        }
    }

    @Override
    public List<Result> findUserResults(String role) throws ServiceException {
        return null;
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
}
