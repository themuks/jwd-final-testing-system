package com.kuntsevich.testsys.model.service.factory;

import com.kuntsevich.testsys.model.dao.DaoException;
import com.kuntsevich.testsys.model.service.ResultService;
import com.kuntsevich.testsys.model.service.SubjectService;
import com.kuntsevich.testsys.model.service.TestService;
import com.kuntsevich.testsys.model.service.UserService;
import com.kuntsevich.testsys.model.service.impl.ResultServiceImpl;
import com.kuntsevich.testsys.model.service.impl.SubjectServiceImpl;
import com.kuntsevich.testsys.model.service.impl.TestServiceImpl;
import com.kuntsevich.testsys.model.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static volatile ServiceFactory instance;
    private TestService testService = new TestServiceImpl();
    private UserService userService = new UserServiceImpl();
    private ResultService resultService = new ResultServiceImpl();
    private SubjectService subjectService = new SubjectServiceImpl();

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

    public UserService getUserService() {
        return userService;
    }

    public ResultService getResultService() {
        return resultService;
    }

    public SubjectService getSubjectService() {
        return subjectService;
    }
}
