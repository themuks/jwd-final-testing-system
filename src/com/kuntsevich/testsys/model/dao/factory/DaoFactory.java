package com.kuntsevich.testsys.model.dao.factory;

import com.kuntsevich.testsys.model.dao.*;
import com.kuntsevich.testsys.model.dao.impl.*;

public class DaoFactory {
    private static volatile DaoFactory instance;
    private TestDao testDao = new SqlTestDaoImpl();
    private SubjectDao subjectDao = new SqlSubjectDaoImpl();
    private StatusDao statusDao = new SqlStatusDaoImpl();
    private RoleDao roleDao = new SqlRoleDaoImpl();
    private UserDao userDao = new SqlUserDaoImpl();

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

    public TestDao getTestDao() {
        return testDao;
    }

    public SubjectDao getSubjectDao() {
        return subjectDao;
    }

    public StatusDao getStatusDao() {
        return statusDao;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
