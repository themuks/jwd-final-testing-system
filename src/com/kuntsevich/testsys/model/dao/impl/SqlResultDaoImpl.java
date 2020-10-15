package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.entity.Result;
import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.entity.User;
import com.kuntsevich.testsys.model.dao.ResultDao;
import com.kuntsevich.testsys.model.dao.exception.DaoException;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;
import com.kuntsevich.testsys.model.dao.pool.DatabaseConnectionPool;
import com.kuntsevich.testsys.model.dao.pool.exception.DatabasePoolException;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;

import java.sql.*;
import java.util.*;

public class SqlResultDaoImpl implements ResultDao {
    private static final String FIND_RESULT_BY_CRITERIA_QUERY = "SELECT result_id, user, test, points FROM testing_system.results WHERE ";
    private static final String DELETE_RESULT_QUERY = "DELETE FROM `testing_system`.`results` WHERE (`result_id` = ?)";
    private static final String RESULT_ID = "result_id";
    private static final String INSERT_RESULT_QUERY = "INSERT INTO testing_system.results (user, test, points) VALUES (?, ?, ?)";
    private static final String FIND_ALL_RESULT_QUERY = "SELECT result_id, user, test, points FROM testing_system.results";
    private static final String EMPTY_STRING = "";

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
    public List<Result> findByCriteria(Map<String, String> criteria) throws DaoException {
        List<Result> results = new ArrayList<>();
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
        String query = "";
        try {
            DaoUtil daoUtil = new DaoUtil();
            query = daoUtil.createQueryWithCriteria(FIND_RESULT_BY_CRITERIA_QUERY, criteria);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                long resultId = rs.getLong(1);
                long userId = rs.getLong(2);
                Optional<User> userOptional = DaoFactory.getInstance().getUserDao().findById(userId);
                User user = userOptional.isPresent() ? userOptional.get() : new User();
                long testId = rs.getLong(3);
                Optional<Test> testOptional = DaoFactory.getInstance().getTestDao().findById(testId);
                Test test = testOptional.isPresent() ? testOptional.get() : new Test();
                int points = rs.getInt(4);
                Result result = new Result(resultId, user, test, points);
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
        List<Result> results = new ArrayList<>();
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
        String query = EMPTY_STRING;
        try {
            query = FIND_ALL_RESULT_QUERY;
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                long resultId = rs.getLong(1);
                long userId = rs.getLong(2);
                Optional<User> userOptional = DaoFactory.getInstance().getUserDao().findById(userId);
                User user = userOptional.isPresent() ? userOptional.get() : new User();
                long testId = rs.getLong(3);
                Optional<Test> testOptional = DaoFactory.getInstance().getTestDao().findById(testId);
                Test test = testOptional.isPresent() ? testOptional.get() : new Test();
                int points = rs.getInt(4);
                Result result = new Result(resultId, user, test, points);
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
    public long add(Result result) throws DaoException {
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
        long id = -1;
        try {
            ps = con.prepareStatement(INSERT_RESULT_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, result.getUser().getUserId());
            ps.setLong(2, result.getTest().getTestId());
            ps.setInt(3, result.getPoints());
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
    public void update(Result result) throws DaoException {
        delete(result);
        add(result);
    }

    @Override
    public void delete(Result result) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
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
