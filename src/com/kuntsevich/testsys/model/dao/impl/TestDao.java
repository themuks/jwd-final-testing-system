package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.connection.DatabaseConnectionPool;
import com.kuntsevich.testsys.entity.Status;
import com.kuntsevich.testsys.entity.Subject;
import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.exception.DaoException;
import com.kuntsevich.testsys.model.dao.Dao;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TestDao implements Dao<Test> {
    private static final String FIND_ALL_TEST_QUERY = "SELECT test_id, title, subject, description, status FROM testing_system.tests";
    private static final String FIND_TEST_BY_CRITERIA_QUERY = "SELECT test_id, title, subject, description, status FROM testing_system.tests WHERE ";
    private static final String TEST_ID = "test_id";

    @Override
    public Optional<Test> findById(long id) throws DaoException {
        Optional<Test> optionalTest = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(TEST_ID, Long.toString(id));
        List<Test> tests = find(criteria);
        if (tests.size() > 0) {
            optionalTest = Optional.of(tests.get(0));
        }
        return optionalTest;
    }

    @Override
    public List<Test> find(Map<String, String> criteria) throws DaoException {
        List<Test> tests = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
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
            DatabaseConnectionPool.getInstance().releaseConnection(con);
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO : Write logs or throw new exception
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO : Write logs or throw new exception
                }
            }
        }
        return tests;
    }

    @Override
    public List<Test> findAll() throws DaoException {
        List<Test> tests = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
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
            DatabaseConnectionPool.getInstance().releaseConnection(con);
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO : Write logs or throw new exception
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    // TODO : Write logs or throw new exception
                }
            }
        }
        return tests;
    }

    @Override
    public void add(Test test) throws DaoException {

    }

    @Override
    public void update(Test test, Map<String, String> params) throws DaoException {

    }

    @Override
    public void delete(Test test) throws DaoException {

    }
}
