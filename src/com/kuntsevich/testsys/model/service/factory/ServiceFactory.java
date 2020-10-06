package com.kuntsevich.testsys.model.service.factory;

import com.kuntsevich.testsys.model.service.TestService;
import com.kuntsevich.testsys.model.service.UserService;
import com.kuntsevich.testsys.model.service.impl.TestServiceImpl;
import com.kuntsevich.testsys.model.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static volatile ServiceFactory instance;
    private TestService testService = new TestServiceImpl();
    private UserService userService = new UserServiceImpl();

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
}
