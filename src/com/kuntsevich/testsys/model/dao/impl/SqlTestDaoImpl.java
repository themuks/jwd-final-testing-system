package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.connection.DatabaseConnectionPool;
import com.kuntsevich.testsys.entity.Status;
import com.kuntsevich.testsys.entity.Subject;
import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.exception.DaoException;
import com.kuntsevich.testsys.exception.DatabasePoolException;
import com.kuntsevich.testsys.model.dao.TestDao;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlTestDaoImpl implements TestDao {
    private static final String FIND_ALL_TEST_QUERY = "SELECT test_id, title, subject, description, status FROM testing_system.tests";
    private static final String FIND_TEST_BY_CRITERIA_QUERY = "SELECT test_id, title, subject, description, status FROM testing_system.tests WHERE ";
    private static final String TEST_ID = "test_id";

    @Override
    public Optional<Test> findById(long id) throws DaoException {
        Optional<Test> optionalTest = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(TEST_ID, Long.toString(id));
        List<Test> tests = findByCriteria(criteria);
        if (tests.size() > 0) {
            optionalTest = Optional.of(tests.get(0));
        }
        return optionalTest;
    }

    @Override
    public List<Test> findByCriteria(Map<String, String> criteria) throws DaoException {
        List<Test> tests = new ArrayList<>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnectionPool.getInstance().getConnection();
        } catch (DatabasePoolException e) {
            throw new DaoException("Can't get connection from database connection pool", e);
        } catch (SQLException e) {
            throw new DaoException("Can't get instance of database connection pool to get connection", e);
        }
        if (con == null) {
            throw new DaoException("Connection is null");
        }
        try {
            DaoUtil daoUtil = new DaoUtil();
            ps = con.prepareStatement(daoUtil.createQueryWithCriteria(FIND_TEST_BY_CRITERIA_QUERY, criteria));
            rs = ps.executeQuery();
            while (rs.next()) {
                long testId = rs.getLong(1);
                String title = rs.getString(2);
                long subjectId = rs.getLong(3);
                Optional<Subject> subjectOptional = DaoFactory.getInstance().getSubjectDao().findById(subjectId);
                Subject subject = subjectOptional.isPresent() ? subjectOptional.get() : new Subject();
                String description = rs.getString(4);
                long statusId = rs.getLong(5);
                Optional<Status> statusOptional = DaoFactory.getInstance().getStatusDao().findById(statusId);
                Status status = statusOptional.isPresent() ? statusOptional.get() : new Status();
                Test test = new Test(testId, title, subject, description, status);
                tests.add(test);
            }
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
        return tests;
    }

    @Override
    public List<Test> findAll() throws DaoException {
        List<Test> tests = new ArrayList<>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DatabaseConnectionPool.getInstance().getConnection();
        } catch (DatabasePoolException e) {
            throw new DaoException("Can't get connection from database connection pool", e);
        } catch (SQLException e) {
            throw new DaoException("Can't get instance of database connection pool to get connection", e);
        }
        if (con == null) {
            throw new DaoException("Connection is null");
        }
        try {
            ps = con.prepareStatement(FIND_ALL_TEST_QUERY);
            rs = ps.executeQuery();
            while (rs.next()) {
                long testId = rs.getLong(1);
                String title = rs.getString(2);
                long subjectId = rs.getLong(3);
                Optional<Subject> subjectOptional = DaoFactory.getInstance().getSubjectDao().findById(subjectId);
                Subject subject = subjectOptional.isPresent() ? subjectOptional.get() : new Subject();
                String description = rs.getString(4);
                long statusId = rs.getLong(5);
                Optional<Status> statusOptional = DaoFactory.getInstance().getStatusDao().findById(statusId);
                Status status = statusOptional.isPresent() ? statusOptional.get() : new Status();
                Test test = new Test(testId, title, subject, description, status);
                tests.add(test);
            }
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
        return tests;
    }
}
