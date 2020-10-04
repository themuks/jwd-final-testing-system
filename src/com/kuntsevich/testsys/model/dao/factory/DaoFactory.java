package com.kuntsevich.testsys.model.dao.factory;

import com.kuntsevich.testsys.entity.*;
import com.kuntsevich.testsys.model.dao.Dao;
import com.kuntsevich.testsys.model.dao.impl.*;

public class DaoFactory {
    private static volatile DaoFactory instance;
    private Dao<Test> testDao = new SqlTestDaoImpl();
    private Dao<Subject> subjectDao = new SqlSubjectDaoImpl();
    private Dao<Status> statusDao = new SqlStatusDaoImpl();
    private Dao<Role> roleDao = new SqlRoleDaoImpl();
    private Dao<User> userDao = new SqlUserDaoImpl();

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        if (instance == null) {
            synchronized (DaoFactory.class) {
                if (instance == null) {
                    instance = new DaoFactory();
                }
            }
        }
        return instance;
    }

    public Dao<Test> getTestDao() {
        return testDao;
    }

    public Dao<Subject> getSubjectDao() {
        return subjectDao;
    }

    public Dao<Status> getStatusDao() {
        return statusDao;
    }

    public Dao<Role> getRoleDao() {
        return roleDao;
    }

    public Dao<User> getUserDao() {
        return userDao;
    }
}
