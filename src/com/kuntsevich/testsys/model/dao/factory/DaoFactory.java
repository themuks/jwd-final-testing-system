package com.kuntsevich.testsys.model.dao.factory;

import com.kuntsevich.testsys.model.dao.*;
import com.kuntsevich.testsys.model.dao.impl.*;

public class DaoFactory {
    private static volatile DaoFactory instance;
    private final TestDao testDao = new SqlTestDaoImpl();
    private final SubjectDao subjectDao = new SqlSubjectDaoImpl();
    private final StatusDao statusDao = new SqlStatusDaoImpl();
    private final RoleDao roleDao = new SqlRoleDaoImpl();
    private final UserDao userDao = new SqlUserDaoImpl();
    private final QuestionDao questionDao = new SqlQuestionDaoImpl();
    private final AnswerDao answerDao = new SqlAnswerDaoImpl();

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

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    public AnswerDao getAnswerDao() {
        return answerDao;
    }
}
