package com.kuntsevich.ts.model.service.factory;

import com.kuntsevich.ts.model.service.ResultService;
import com.kuntsevich.ts.model.service.SubjectService;
import com.kuntsevich.ts.model.service.TestService;
import com.kuntsevich.ts.model.service.UserService;
import com.kuntsevich.ts.model.service.impl.ResultServiceImpl;
import com.kuntsevich.ts.model.service.impl.SubjectServiceImpl;
import com.kuntsevich.ts.model.service.impl.TestServiceImpl;
import com.kuntsevich.ts.model.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static volatile ServiceFactory instance;
    private final TestService testService = new TestServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final ResultService resultService = new ResultServiceImpl();
    private final SubjectService subjectService = new SubjectServiceImpl();

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
