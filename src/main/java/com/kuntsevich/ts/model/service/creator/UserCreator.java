package com.kuntsevich.ts.model.service.creator;

import com.kuntsevich.ts.entity.Role;
import com.kuntsevich.ts.entity.Status;
import com.kuntsevich.ts.entity.User;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.RoleDao;
import com.kuntsevich.ts.model.dao.StatusDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;
import com.kuntsevich.ts.model.service.exception.CreatorException;
import com.kuntsevich.ts.validator.UserValidator;
import org.apache.log4j.Logger;

public class UserCreator {
    private static final Logger log = Logger.getLogger(UserCreator.class);
    private static final String STATUS_ACTIVE = "В ожидании";
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
        if (!UserValidator.isUsernameValid(username)
                || !UserValidator.isNameValid(name)
                || !UserValidator.isSurnameValid(surname)
                || !UserValidator.isRoleValid(role)) {
            throw new CreatorException("Parameters are incorrect");
        }
        DaoFactory daoFactory = DaoFactory.getInstance();
        RoleDao roleDao = daoFactory.getRoleDao();
        Role roleFromDb;
        try {
            roleFromDb = roleDao.findByName(role).orElse(new Role());
        } catch (DaoException e) {
            log.error(e);
            throw new CreatorException("Error finding role by name", e);
        }
        StatusDao statusDao = daoFactory.getStatusDao();
        Status statusFromDb;
        try {
            statusFromDb = statusDao.findByName(STATUS_ACTIVE).orElse(new Status());
        } catch (DaoException e) {
            log.error(e);
            throw new CreatorException("Error finding status by name", e);
        }
        return new User(username, name, surname, emailHash, passwordHash, EMPTY_STRING, roleFromDb, statusFromDb);
    }
}
