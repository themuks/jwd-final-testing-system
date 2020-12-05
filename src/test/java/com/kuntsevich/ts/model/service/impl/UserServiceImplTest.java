package com.kuntsevich.ts.model.service.impl;

import com.kuntsevich.ts.entity.Credential;
import com.kuntsevich.ts.entity.Role;
import com.kuntsevich.ts.entity.Status;
import com.kuntsevich.ts.entity.User;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.UserDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;
import com.kuntsevich.ts.model.service.UserService;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.model.service.factory.ServiceFactory;
import org.apache.log4j.Logger;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class UserServiceImplTest {
    static Logger log = Logger.getLogger(UserServiceImplTest.class);

    @Mock
    DaoFactory daoFactory;

    @InjectMocks
    UserService userService = ServiceFactory.getInstance().getUserService();

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkLogin_CorrectInput_Credentials() {
        UserDao userDaoMock = Mockito.mock(UserDao.class);
        try {
            when(userDaoMock.findByEmailAndPasswordHash(any(), any()))
                    .thenReturn(
                            Optional.of(new User(1,
                                    "username",
                                    "name",
                                    "surname",
                                    "test@test.test",
                                    "",
                                    "",
                                    new Role("Студент"),
                                    new Status("Активный"))));
        } catch (DaoException e) {
            log.error(e);
            fail("Mock error", e);
        }
        when(daoFactory.getUserDao()).thenReturn(userDaoMock);
        try {
            Credential credential = new Credential(1, "", "test@test.test");
            Optional<Credential> expected = Optional.of(credential);
            Optional<Credential> actual = userService.checkLogin("test@test.test", "123456");
            log.info("Actual: " + actual);
            log.info("Expected: " + expected);
            assertEquals(actual.get().getEmail(), expected.get().getEmail());
            assertEquals(actual.get().getUserId(), expected.get().getUserId());
            assertNotNull(actual.get().getUserHash());
        } catch (ServiceException e) {
            log.error(e);
            fail("Error while checking login", e);
        }
    }

    @Test
    public void checkLogin_IncorrectInput_EmptyOptional() {
        try {
            Optional<Credential> expected = Optional.empty();
            Optional<Credential> actual = userService.checkLogin(null, "1");
            log.info("Actual: " + actual);
            log.info("Expected: " + expected);
            assertEquals(actual, expected);
        } catch (ServiceException e) {
            log.error(e);
            fail("Error while checking login", e);
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void checkLogin_DaoFindException_ServiceException() throws ServiceException {
        UserDao userDaoMock = Mockito.mock(UserDao.class);
        try {
            when(userDaoMock.findByEmailAndPasswordHash(any(), any()))
                    .thenThrow(new DaoException());
        } catch (DaoException e) {
            log.error(e);
            fail("Mock error", e);
        }
        when(daoFactory.getUserDao()).thenReturn(userDaoMock);
        userService.checkLogin("test@test.test", "123456");
    }

    @Test(expectedExceptions = ServiceException.class)
    public void checkLogin_DaoUpdateException_ServiceException() throws ServiceException {
        UserDao userDaoMock = Mockito.mock(UserDao.class);
        try {
            when(userDaoMock.findByEmailAndPasswordHash(any(), any()))
                    .thenReturn(
                            Optional.of(new User(1,
                                    "username",
                                    "name",
                                    "surname",
                                    "test@test.test",
                                    "",
                                    "",
                                    new Role("Студент"),
                                    new Status("Активный"))));
            doThrow(DaoException.class).when(userDaoMock).update(any());
        } catch (DaoException e) {
            log.error(e);
            fail("Mock error", e);
        }
        when(daoFactory.getUserDao()).thenReturn(userDaoMock);
        userService.checkLogin("test@test.test", "123456");
    }
}