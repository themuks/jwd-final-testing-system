package com.kuntsevich.ts.model.service.impl;

import com.kuntsevich.ts.controller.manager.MessageManager;
import com.kuntsevich.ts.entity.Credential;
import com.kuntsevich.ts.entity.Result;
import com.kuntsevich.ts.entity.Status;
import com.kuntsevich.ts.entity.User;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.StatusDao;
import com.kuntsevich.ts.model.dao.UserDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;
import com.kuntsevich.ts.model.service.UserService;
import com.kuntsevich.ts.model.service.creator.CredentialCreator;
import com.kuntsevich.ts.model.service.creator.UserCreator;
import com.kuntsevich.ts.model.service.exception.CreatorException;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.validator.EmailValidator;
import com.kuntsevich.ts.validator.EntityValidator;
import com.kuntsevich.ts.validator.NumberValidator;
import com.kuntsevich.ts.validator.UserValidator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private static final String HEX_FORMAT_STRING = "%02x";
    private static final String MESSAGE_DIGEST_MD5 = "MD5";
    private static final String SALT = "s7l3T1hTEA3";
    private static final String INACTIVE = "Неактивный";
    private static final String ADMIN = "Администратор";
    private static final String TUTOR = "Тьютор";
    private static final String PROMO_CODE = "promo";
    private static final String PENDING = "В ожидании";
    private static final String CONFIG_MAIL = "config.mail";
    private static final String CONFIG_CREDENTIALS = "config.credentials";
    private static final String USERNAME = "credentials.username";
    private static final String PASSWORD = "credentials.password";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String MESSAGE_EMAIL_VERIFICATION_SUBJECT = "message.email.verification.subject";
    private static final String MESSAGE_EMAIL_VERIFICATION_TEXT = "message.email.verification.text";
    private static final String EMAIL_LINK = "<a href=\"http://localhost:8080/controller?command=%s&userId=%d&secretKey=%s\">%s</a>";
    private static final String ACTIVE = "Активный";
    private static final String MESSAGE_EMAIL_RECOVERY_SUBJECT = "message.email.recovery.subject";
    private static final String MESSAGE_EMAIL_RECOVERY_TEXT = "message.email.recovery.text";
    private static final String PASSWORD_RESET = "password-reset";
    private static final String ACTIVATE_ACCOUNT = "activate-account";
    private static final String UTF_8 = "utf-8";
    private static final String HTML = "html";
    private static final String MESSAGE_EMAIL_VERIFICATION_PRESS = "message.email.verification.press";

    @Override
    public Optional<Credential> checkLogin(String email, String password) throws ServiceException {
        if (email == null || password == null) {
            throw new ServiceException("Parameters are null");
        }
        if (!UserValidator.isEmailValid(email) || !UserValidator.isPasswordValid(password)) {
            throw new ServiceException("Parameters are invalid");
        }
        Optional<Credential> optionalCredential = Optional.empty();
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        String passwordHash;
        try {
            passwordHash = calculateMdHash(password + SALT);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Can't find MD5 algorithm", e);
        }
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findByEmailAndPasswordHash(email, passwordHash);
        } catch (DaoException e) {
            throw new ServiceException("Error finding user by email and passwordHash", e);
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String roleName = user.getStatus().getName();
            if (INACTIVE.equals(roleName) || PENDING.equals(roleName)) {
                return Optional.empty();
            }
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
            Credential credential = new Credential(user.getUserId(), user.getUserHash(), user.getEmail());
            optionalCredential = Optional.of(credential);
        }
        return optionalCredential;
    }

    @Override
    public boolean registration(String username, String name, String surname, String email, String password, String role, String promo) throws ServiceException {
        if (username == null
                || name == null
                || surname == null
                || email == null
                || password == null
                || role == null) {
            return false;
        }
        if (!UserValidator.isUsernameValid(username)
                || !UserValidator.isNameValid(name)
                || !UserValidator.isSurnameValid(surname)
                || !UserValidator.isEmailValid(email)
                || !UserValidator.isPasswordValid(password)
                || !UserValidator.isRoleValid(role)) {
            return false;
        }
        if (role.equals(ADMIN)) {
            return false;
        }
        if (role.equals(TUTOR) && !PROMO_CODE.equals(promo)) {
            return false;
        }
        String passwordHash;
        try {
            passwordHash = calculateMdHash(password + SALT);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Can't find MD5 algorithm", e);
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException("Error finding user by email", e);
        }
        if (optionalUser.isPresent()) {
            return false;
        }
        User user;
        try {
            user = UserCreator.createUser(username, name, surname, email, passwordHash, role);
        } catch (CreatorException e) {
            throw new ServiceException("Error creating user", e);
        }
        try {
            long id = userDao.add(user);
            user.setUserId(id);
        } catch (DaoException e) {
            throw new ServiceException("User dao add error", e);
        }
        if (!sendVerificationEmail(email, user)) {
            try {
                userDao.delete(user);
            } catch (DaoException e) {
                throw new ServiceException("User dao delete error", e);
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean authorization(String email, String userHash) throws ServiceException {
        if (email == null || userHash == null) {
            return false;
        }
        if (!EntityValidator.isIdValid(email)) {
            return false;
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        Credential credential;
        try {
            credential = CredentialCreator.createCredential(userHash, email);
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
        if (!EntityValidator.isIdValid(id)) {
            throw new ServiceException("Parameters are incorrect");
        }
        UserDao userDao = DaoFactory.getInstance().getUserDao();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public User findUserById(String userId) throws ServiceException {
        if (userId == null) {
            throw new ServiceException("Parameter is null");
        }
        if (!EntityValidator.isIdValid(userId)) {
            throw new ServiceException("User id is invalid");
        }
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            Optional<User> userOptional = userDao.findById(Long.parseLong(userId));
            if (userOptional.isPresent()) {
                return userOptional.get();
            } else {
                throw new ServiceException("User isn't exist");
            }
        } catch (DaoException e) {
            throw new ServiceException("Error finding user by id", e);
        }
    }

    @Override
    public boolean changeUserPassword(String userId, String oldPassword, String newPassword, String newPasswordAgain) throws ServiceException {
        if (userId == null
                || oldPassword == null
                || newPassword == null
                || newPasswordAgain == null) {
            return false;
        }
        if (!EntityValidator.isIdValid(userId)
                || !UserValidator.isPasswordValid(oldPassword)
                || !UserValidator.isPasswordValid(newPassword)
                || !UserValidator.isPasswordValid(newPasswordAgain)) {
            return false;
        }
        if (newPassword.equals(newPasswordAgain)) {
            try {
                String oldPasswordHash = calculateMdHash(oldPassword + SALT);
                String newPasswordHash = calculateMdHash(newPassword + SALT);
                UserDao userDao = DaoFactory.getInstance().getUserDao();
                Optional<User> optionalUser;
                try {
                    optionalUser = userDao.findByUserIdAndPasswordHash(Long.parseLong(userId), oldPasswordHash);
                } catch (DaoException e) {
                    throw new ServiceException("Error while finding user by user id and password hash", e);
                }
                if (optionalUser.isEmpty()) {
                    throw new ServiceException("User not found");
                }
                User user = optionalUser.get();
                user.setPasswordHash(newPasswordHash);
                try {
                    userDao.update(user);
                } catch (DaoException e) {
                    throw new ServiceException("Error while updating user", e);
                }
            } catch (NoSuchAlgorithmException e) {
                throw new ServiceException("Can't find MD5 algorithm", e);
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean changeUserData(String userId, String username, String name, String surname) throws ServiceException {
        if (userId == null
                || username == null
                || name == null
                || surname == null) {
            return false;
        }
        if (!EntityValidator.isIdValid(userId)
                || !UserValidator.isUsernameValid(username)
                || !UserValidator.isNameValid(name)
                || !UserValidator.isSurnameValid(surname)) {
            return false;
        }
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findById(Long.parseLong(userId));
        } catch (DaoException e) {
            throw new ServiceException("Error while finding user by id", e);
        }
        if (optionalUser.isEmpty()) {
            throw new ServiceException("User not found");
        }
        User user = optionalUser.get();
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException("Error while updating user", e);
        }
        return true;
    }

    @Override
    public String findUserUsername(String id) throws ServiceException {
        if (id == null) {
            throw new ServiceException("Parameters are null");
        }
        if (!EntityValidator.isIdValid(id)) {
            throw new ServiceException("Parameters are incorrect");
        }
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        long idValue = Long.parseLong(id);
        Optional<User> userOptional;
        try {
            userOptional = userDao.findById(idValue);
        } catch (DaoException e) {
            throw new ServiceException("Error finding user by id", e);
        }
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getUsername();
        } else {
            throw new ServiceException("User is not exist");
        }
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Error while finding all users", e);
        }
    }

    @Override
    public int findPageCount(String recordsPerPage) throws ServiceException {
        if (recordsPerPage == null) {
            throw new ServiceException("Parameter is null");
        }
        if (!NumberValidator.isIntegerValid(recordsPerPage)) {
            throw new ServiceException("Page is invalid");
        }
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            int recordsCount = userDao.findCount();
            int recordsPerPageInt = Integer.parseInt(recordsPerPage);
            int pageCount = recordsCount / recordsPerPageInt;
            pageCount = recordsCount % recordsPerPageInt > 0 ? pageCount + 1 : pageCount;
            return pageCount;
        } catch (DaoException e) {
            throw new ServiceException("Error while finding user count");
        }
    }

    @Override
    public List<User> findPageUsers(String userId, String role, String page, String recordsPerPage) throws ServiceException {
        if (userId == null
                || role == null
                || page == null
                || recordsPerPage == null) {
            throw new ServiceException("Parameters are null");
        }
        if (!EntityValidator.isIdValid(userId)
                || !UserValidator.isRoleValid(role)
                || !NumberValidator.isIntegerValid(page)
                || !NumberValidator.isIntegerValid(recordsPerPage)) {
            throw new ServiceException("Parameters are invalid");
        }
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int recordsPerPageInt = Integer.parseInt(recordsPerPage);
        try {
            List<User> users = userDao.findWithLimits(recordsPerPageInt * (Integer.parseInt(page) - 1), recordsPerPageInt);
            if (!ADMIN.equals(userId)) {
                long userIdLong = Long.parseLong(userId);
                users = users.stream()
                        .filter(u -> !u.getRole().getName().equals(ADMIN))
                        .filter(u -> u.getUserId() != userIdLong)
                        .collect(Collectors.toList());
            }
            return users;
        } catch (DaoException e) {
            throw new ServiceException("Error while finding users with limits");
        }
    }

    @Override
    public boolean deactivateAccount(String userId) throws ServiceException {
        if (userId == null) {
            return false;
        }
        if (!EntityValidator.isIdValid(userId)) {
            return false;
        }
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findById(Long.parseLong(userId));
        } catch (DaoException e) {
            throw new ServiceException("Error while finding user by id", e);
        }
        if (optionalUser.isEmpty()) {
            return false;
        }
        User user = optionalUser.get();
        StatusDao statusDao = DaoFactory.getInstance().getStatusDao();
        Optional<Status> optionalStatus;
        try {
            optionalStatus = statusDao.findByName(INACTIVE);
        } catch (DaoException e) {
            throw new ServiceException("Error while finding status by name", e);
        }
        if (optionalStatus.isEmpty()) {
            return false;
        }
        Status status = optionalStatus.get();
        if (user.getStatus().equals(status)) {
            return false;
        }
        user.setStatus(status);
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException("Error while updating user", e);
        }
        return true;
    }

    @Override
    public boolean resetPassword(String userId, String newPassword, String secretKey) throws ServiceException {
        if (userId == null || newPassword == null || secretKey == null) {
            return false;
        }
        if (!EntityValidator.isIdValid(userId) || !UserValidator.isPasswordValid(newPassword)) {
            return false;
        }
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findById(Long.parseLong(userId));
        } catch (DaoException e) {
            throw new ServiceException("Error while finding user by email", e);
        }
        if (optionalUser.isEmpty()) {
            return false;
        }
        User user = optionalUser.get();
        String userSecretKey;
        try {
            userSecretKey = calculateMdHash(user.getUserId() + user.getEmail() + user.getPasswordHash() + SALT);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Error while calculating md hash", e);
        }
        if (!secretKey.equals(userSecretKey)) {
            return false;
        }
        String passwordHash;
        try {
            passwordHash = calculateMdHash(newPassword + SALT);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Can't find MD5 algorithm", e);
        }
        user.setPasswordHash(passwordHash);
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException("Error while updating user", e);
        }
        return true;
    }

    @Override
    public boolean sendPasswordRecoveryEmail(String email) throws ServiceException {
        if (email == null) {
            return false;
        }
        if (!UserValidator.isEmailValid(email)) {
            return false;
        }
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException("Error while finding user by email", e);
        }
        if (optionalUser.isEmpty()) {
            return false;
        }
        User user = optionalUser.get();
        String subject = MessageManager.getProperty(MESSAGE_EMAIL_RECOVERY_SUBJECT);
        String text = MessageManager.getProperty(MESSAGE_EMAIL_RECOVERY_TEXT);
        String press = MessageManager.getProperty(MESSAGE_EMAIL_VERIFICATION_PRESS);
        String secretKey;
        try {
            secretKey = calculateMdHash(user.getUserId() + user.getEmail() + user.getPasswordHash() + SALT);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Error while calculating md hash", e);
        }
        String link = String.format(EMAIL_LINK, PASSWORD_RESET, user.getUserId(), secretKey, press);
        text = text + System.lineSeparator() + link;
        return sendEmail(email, subject, text);
    }

    @Override
    public boolean activateAccount(String userId, String secretKey) throws ServiceException {
        if (userId == null || secretKey == null) {
            return false;
        }
        if (!EntityValidator.isIdValid(userId)) {
            return false;
        }
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.findById(Long.parseLong(userId));
        } catch (DaoException e) {
            throw new ServiceException("Error while finding user by id", e);
        }
        if (optionalUser.isEmpty()) {
            return false;
        }
        User user = optionalUser.get();
        if (!PENDING.equals(user.getStatus().getName())) {
            return false;
        }
        String generatedSecretKey;
        try {
            generatedSecretKey = calculateMdHash(user.getEmail() + user.getPasswordHash() + SALT);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Error while calculating md hash", e);
        }
        if (!secretKey.equals(generatedSecretKey)) {
            return false;
        }
        StatusDao statusDao = DaoFactory.getInstance().getStatusDao();
        Optional<Status> optionalStatus;
        try {
            optionalStatus = statusDao.findByName(ACTIVE);
        } catch (DaoException e) {
            throw new ServiceException("Error while finding status by name", e);
        }
        if (optionalStatus.isEmpty()) {
            return false;
        }
        Status status = optionalStatus.get();
        if (user.getStatus().equals(status)) {
            return false;
        }
        user.setStatus(status);
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException("Error while updating user", e);
        }
        return true;
    }

    @Override
    public boolean sendVerificationEmail(String email, User user) throws ServiceException {
        if (email == null || user == null) {
            return false;
        }
        if (!UserValidator.isEmailValid(email)) {
            return false;
        }
        String subject = MessageManager.getProperty(MESSAGE_EMAIL_VERIFICATION_SUBJECT);
        String text = MessageManager.getProperty(MESSAGE_EMAIL_VERIFICATION_TEXT);
        String press = MessageManager.getProperty(MESSAGE_EMAIL_VERIFICATION_PRESS);
        String secretKey;
        try {
            secretKey = calculateMdHash(user.getEmail() + user.getPasswordHash() + SALT);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Error while calculating md hash", e);
        }
        String link = String.format(EMAIL_LINK, ACTIVATE_ACCOUNT, user.getUserId(), secretKey, press);
        text = text + System.lineSeparator() + link;
        return sendEmail(email, subject, text);
    }

    private boolean sendEmail(String to, String subject, String text) {
        if (to == null
                || subject == null
                || text == null) {
            return false;
        }
        if (!UserValidator.isEmailValid(to)
                || !EmailValidator.isSubjectValid(subject)
                || !EmailValidator.isTextValid(text)) {
            return false;
        }
        ResourceBundle credentialBundle = ResourceBundle.getBundle(CONFIG_CREDENTIALS);
        ResourceBundle mailBundle = ResourceBundle.getBundle(CONFIG_MAIL);
        String username = credentialBundle.getString(USERNAME);
        String password = credentialBundle.getString(PASSWORD);
        String auth = mailBundle.getString(MAIL_SMTP_AUTH);
        String starttls = mailBundle.getString(MAIL_SMTP_STARTTLS_ENABLE);
        String host = mailBundle.getString(MAIL_SMTP_HOST);
        String port = mailBundle.getString(MAIL_SMTP_PORT);
        Properties properties = new Properties();
        properties.put(MAIL_SMTP_AUTH, auth);
        properties.put(MAIL_SMTP_STARTTLS_ENABLE, starttls);
        properties.put(MAIL_SMTP_HOST, host);
        properties.put(MAIL_SMTP_PORT, port);
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text, UTF_8, HTML);
            Transport.send(message);
        } catch (MessagingException e) {
            return false;
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
}
