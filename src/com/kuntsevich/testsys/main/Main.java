package com.kuntsevich.testsys.main;

import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.service.factory.ServiceFactory;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(ServiceFactory.getInstance().getTestService().findAll());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
