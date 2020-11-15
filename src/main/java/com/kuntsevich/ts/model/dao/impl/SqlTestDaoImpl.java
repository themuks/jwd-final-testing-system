package com.kuntsevich.ts.model.dao.impl;

import com.kuntsevich.ts.entity.*;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.TestDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;
import com.kuntsevich.ts.model.dao.pool.DatabaseConnectionPool;
import com.kuntsevich.ts.model.dao.util.DaoUtil;

import java.sql.*;
import java.util.*;

public class SqlTestDaoImpl implements TestDao {
    private static final String FIND_ALL_TEST_QUERY = "SELECT test_id, title, subject, description, status, points_to_pass FROM testing_system.tests";
    private static final String TEST_ID = "test_id";
    private static final String INSERT_TEST_QUERY = "INSERT INTO testing_system.tests (title, subject, description, status, points_to_pass) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_TEST_QUERY = "UPDATE testing_system.tests SET title = ?, subject = ?, description = ?, status = ?, points_to_pass = ? WHERE (test_id = ?)";
    private static final String DELETE_TEST_QUERY = "DELETE FROM testing_system.tests WHERE (test_id = ?)";
    private static final String SUBJECT = "subject";
    private static final String EMPTY_STRING = "";
    private static final String FIND_TEST_WITH_LIMITS_QUERY = "SELECT test_id, title, subject, description, status, points_to_pass FROM testing_system.tests ORDER BY test_id DESC LIMIT ? OFFSET ?";
    private static final String FIND_COUNT_OF_TESTS_QUERY = "SELECT count(*) FROM testing_system.tests";

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
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            DaoUtil daoUtil = new DaoUtil();
            ps = con.prepareStatement(daoUtil.createQueryWithCriteria(FIND_ALL_TEST_QUERY, criteria));
            rs = ps.executeQuery();
            while (rs.next()) {
                long testId = rs.getLong(1);
                String title = rs.getString(2);
                long subjectId = rs.getLong(3);
                Optional<Subject> subjectOptional = DaoFactory.getInstance().getSubjectDao().findById(subjectId);
                Subject subject = subjectOptional.orElseGet(Subject::new);
                String description = rs.getString(4);
                long statusId = rs.getLong(5);
                Optional<Status> statusOptional = DaoFactory.getInstance().getStatusDao().findById(statusId);
                Status status = statusOptional.orElseGet(Status::new);
                List<Question> questions = DaoFactory.getInstance().getQuestionDao().findByTestId(testId);
                int pointsToPass = rs.getInt(6);
                Test test = new Test(testId, title, subject, description, questions, status, pointsToPass);
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
        return findByCriteria(new HashMap<>());
    }

    @Override
    public long add(Test test) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        long id = -1;
        try {
            ps = con.prepareStatement(INSERT_TEST_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, test.getTitle());
            ps.setLong(2, test.getSubject().getSubjectId());
            ps.setString(3, test.getDescription());
            ps.setLong(4, test.getStatus().getStatusId());
            ps.setInt(5, test.getPointsToPass());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
        return id;
    }

    @Override
    public void update(Test test) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            ps = con.prepareStatement(UPDATE_TEST_QUERY);
            ps.setString(1, test.getTitle());
            ps.setLong(2, test.getSubject().getSubjectId());
            ps.setString(3, test.getDescription());
            ps.setLong(4, test.getStatus().getStatusId());
            ps.setInt(5, test.getPointsToPass());
            ps.setLong(6, test.getTestId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps);
        }
    }

    @Override
    public void delete(Test test) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            ps = con.prepareStatement(DELETE_TEST_QUERY);
            ps.setLong(1, test.getTestId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps);
        }
    }

    @Override
    public List<Test> findBySubjectId(long subjectId) throws DaoException {
        HashMap<String, String> criteria = new HashMap<>();
        criteria.put(SUBJECT, Long.toString(subjectId));
        return findByCriteria(criteria);
    }

    @Override
    public List<Test> findWithLimits(int offset, int limit) throws DaoException {
        List<Test> tests = new ArrayList<>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            ps = con.prepareStatement(FIND_TEST_WITH_LIMITS_QUERY);
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            rs = ps.executeQuery();
            while (rs.next()) {
                long testId = rs.getLong(1);
                String title = rs.getString(2);
                long subjectId = rs.getLong(3);
                Optional<Subject> subjectOptional = DaoFactory.getInstance().getSubjectDao().findById(subjectId);
                Subject subject = subjectOptional.orElseGet(Subject::new);
                String description = rs.getString(4);
                long statusId = rs.getLong(5);
                Optional<Status> statusOptional = DaoFactory.getInstance().getStatusDao().findById(statusId);
                Status status = statusOptional.orElseGet(Status::new);
                List<Question> questions = DaoFactory.getInstance().getQuestionDao().findByTestId(testId);
                int pointsToPass = rs.getInt(6);
                Test test = new Test(testId, title, subject, description, questions, status, pointsToPass);
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
    public int findCount() throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        String query = EMPTY_STRING;
        try {
            query = FIND_COUNT_OF_TESTS_QUERY;
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Error executing query " + query, e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
    }
}
