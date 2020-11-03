package com.kuntsevich.testsys.model.service.creator;

import com.kuntsevich.testsys.entity.Role;
import com.kuntsevich.testsys.entity.Status;
import com.kuntsevich.testsys.entity.User;
import com.kuntsevich.testsys.model.dao.RoleDao;
import com.kuntsevich.testsys.model.dao.StatusDao;
import com.kuntsevich.testsys.model.dao.DaoException;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;
import com.kuntsevich.testsys.model.service.exception.CreatorException;
import com.kuntsevich.testsys.model.service.validator.UserValidator;

public class UserCreator {
    private static final String STATUS_ACTIVE = "Активный";
    private static final String EMPTY_STRING = "";

    public static User createUser(String username, String name, String surname, String emailHash, String passwordHash, String role) throws CreatorException {
        if (username == null
                || name == null
                || surname == null
                || emailHash == null
                || passwordHash == null
                || role == null) {
            throw new CreatorException("Parameters are null");
        }
        UserValidator userValidator = new UserValidator();
        if (!userValidator.isUsernameValid(username)
                || !userValidator.isNameValid(name)
                || !userValidator.isSurnameValid(surname)
                || !userValidator.isRoleValid(role)) {
            throw new CreatorException("Parameters are incorrect");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        RoleDao roleDao = daoFactory.getRoleDao();
        Role roleFromDb;
        try {
            roleFromDb = roleDao.findByName(role).orElse(new Role());
        } catch (DaoException e) {
            throw new CreatorException("Error finding role by name", e);
        }
        StatusDao statusDao = daoFactory.getStatusDao();
        Status statusFromDb;
        try {
            statusFromDb = statusDao.findByName(STATUS_ACTIVE).orElse(new Status());
        } catch (DaoException e) {
            throw new CreatorException("Error finding status by name", e);
        }
        User user = new User(username, name, surname, emailHash, passwordHash, EMPTY_STRING, roleFromDb, statusFromDb);
        return user;
    }
}
