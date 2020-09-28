package com.kuntsevich.testsys.model.dao.factory;

import com.kuntsevich.testsys.entity.Answer;
import com.kuntsevich.testsys.entity.Status;
import com.kuntsevich.testsys.entity.Subject;
import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.model.dao.Dao;
import com.kuntsevich.testsys.model.dao.impl.StatusDao;
import com.kuntsevich.testsys.model.dao.impl.SubjectDao;
import com.kuntsevich.testsys.model.dao.impl.TestDao;

public class DaoFactory {
    private static volatile DaoFactory instance;
    private Dao<Test> testDao = new TestDao();
    private Dao<Subject> subjectDao = new SubjectDao();
    private Dao<Status> statusDao = new StatusDao();

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
}
