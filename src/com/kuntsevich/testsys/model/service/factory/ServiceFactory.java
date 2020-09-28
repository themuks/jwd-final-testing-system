package com.kuntsevich.testsys.model.service.factory;

import com.kuntsevich.testsys.entity.Status;
import com.kuntsevich.testsys.entity.Subject;
import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.model.dao.Dao;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;
import com.kuntsevich.testsys.model.dao.impl.StatusDao;
import com.kuntsevich.testsys.model.dao.impl.SubjectDao;
import com.kuntsevich.testsys.model.dao.impl.TestDao;
import com.kuntsevich.testsys.model.service.TestService;

public class ServiceFactory {
    private static volatile ServiceFactory instance;
    private TestService testService = new TestService();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        if (instance == null) {
            synchronized (ServiceFactory.class) {
                if (instance == null) {
                    instance = new ServiceFactory();
                }
            }
        }
        return instance;
    }

    public TestService getTestService() {
        return testService;
    }
}
