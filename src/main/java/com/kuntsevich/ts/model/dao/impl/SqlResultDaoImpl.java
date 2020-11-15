package com.kuntsevich.ts.model.dao.impl;

import com.kuntsevich.ts.entity.Result;
import com.kuntsevich.ts.entity.Test;
import com.kuntsevich.ts.entity.User;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.ResultDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;
import com.kuntsevich.ts.model.dao.pool.DatabaseConnectionPool;
import com.kuntsevich.ts.model.dao.util.DaoUtil;

import java.sql.*;
import java.util.*;

public class SqlResultDaoImpl implements ResultDao {
    private static final String DELETE_RESULT_QUERY = "DELETE FROM testing_system.results WHERE (result_id = ?)";
    private static final String RESULT_ID = "result_id";
    private static final String INSERT_RESULT_QUERY = "INSERT INTO testing_system.results (user, test, points, correct_answers, total_points) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_ALL_RESULT_QUERY = "SELECT result_id, user, test, points, correct_answers, total_points FROM testing_system.results";
    private static final String USER_ID = "user";
    private static final String EMPTY_STRING = "";
    private static final String TEST_ID = "test";
    private static final String FIND_RESULT_BY_USER_ID_WITH_LIMITS_QUERY = "SELECT result_id, user, test, points, correct_answers, total_points FROM testing_system.results WHERE user = ? ORDER BY result_id DESC LIMIT ? OFFSET ?";
    private static final String FIND_COUNT_OF_RESULTS_BY_USER_ID_QUERY = "SELECT count(user) FROM testing_system.results WHERE user = ?";

    @Override
    public Optional<Result> findById(long id) throws DaoException {
        Optional<Result> optionalResult = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(RESULT_ID, Long.toString(id));
        List<Result> results = findByCriteria(criteria);
        if (results.size() > 0) {
            optionalResult = Optional.of(results.get(0));
        }
        return optionalResult;
    }

    @Override
    public List<Result> findByUserId(long userId) throws DaoException {
        Map<String, String> criteria = new HashMap<>();
        criteria.put(USER_ID, Long.toString(userId));
        return findByCriteria(criteria);
    }

    @Override
    public List<Result> findByTestId(long testId) throws DaoException {
        Map<String, String> criteria = new HashMap<>();
        criteria.put(TEST_ID, Long.toString(testId));
        return findByCriteria(criteria);
    }

    @Override
    public int findCountByUserId(long userId) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        String query = EMPTY_STRING;
        try {
            query = FIND_COUNT_OF_RESULTS_BY_USER_ID_QUERY;
            ps = con.prepareStatement(query);
            ps.setLong(1, userId);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Error executing query " + query, e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
    }

    @Override
    public List<Result> findByUserIdWithLimits(long userId, int offset, int limit) throws DaoException {
        List<Result> results = new ArrayList<>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        String query = EMPTY_STRING;
        try {
            query = FIND_RESULT_BY_USER_ID_WITH_LIMITS_QUERY;
            ps = con.prepareStatement(query);
            ps.setLong(1, userId);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            rs = ps.executeQuery();
            while (rs.next()) {
                long resultId = rs.getLong(1);
                long foundUserId = rs.getLong(2);
                Optional<User> userOptional = DaoFactory.getInstance().getUserDao().findById(foundUserId);
                User user = userOptional.orElseGet(User::new);
                long testId = rs.getLong(3);
                Optional<Test> testOptional = DaoFactory.getInstance().getTestDao().findById(testId);
                Test test = testOptional.orElseGet(Test::new);
                int points = rs.getInt(4);
                int correctAnswers = rs.getInt(5);
                int totalPoints = rs.getInt(6);
                boolean isTestPassed = points >= test.getPointsToPass();
                Result result = new Result(resultId, user, test, points, correctAnswers, totalPoints, isTestPassed);
                results.add(result);
            }
        } catch (SQLException e) {
            throw new DaoException("Error executing query " + query, e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
        return results;
    }

    @Override
    public List<Result> findByCriteria(Map<String, String> criteria) throws DaoException {
        List<Result> results = new ArrayList<>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        String query = EMPTY_STRING;
        try {
            DaoUtil daoUtil = new DaoUtil();
            query = daoUtil.createQueryWithCriteria(FIND_ALL_RESULT_QUERY, criteria);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                long resultId = rs.getLong(1);
                long userId = rs.getLong(2);
                Optional<User> userOptional = DaoFactory.getInstance().getUserDao().findById(userId);
                User user = userOptional.orElseGet(User::new);
                long testId = rs.getLong(3);
                Optional<Test> testOptional = DaoFactory.getInstance().getTestDao().findById(testId);
                Test test = testOptional.orElseGet(Test::new);
                int points = rs.getInt(4);
                int correctAnswers = rs.getInt(5);
                int totalPoints = rs.getInt(6);
                boolean isTestPassed = points >= test.getPointsToPass();
                Result result = new Result(resultId, user, test, points, correctAnswers, totalPoints, isTestPassed);
                results.add(result);
            }
        } catch (SQLException e) {
            throw new DaoException("Error executing query " + query, e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
        return results;
    }

    @Override
    public List<Result> findAll() throws DaoException {
        return findByCriteria(new HashMap<>());
    }

    @Override
    public long add(Result result) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        long id = -1;
        try {
            ps = con.prepareStatement(INSERT_RESULT_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, result.getUser().getUserId());
            ps.setLong(2, result.getTest().getTestId());
            ps.setInt(3, result.getPoints());
            ps.setInt(4, result.getCorrectAnswers());
            ps.setInt(5, result.getTotalPoints());
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
    public void delete(Result result) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            ps = con.prepareStatement(DELETE_RESULT_QUERY);
            ps.setLong(1, result.getResultId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps);
        }
    }
}
