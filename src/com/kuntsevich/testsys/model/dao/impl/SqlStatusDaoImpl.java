package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.connection.DatabaseConnectionPool;
import com.kuntsevich.testsys.entity.Status;
import com.kuntsevich.testsys.exception.DaoException;
import com.kuntsevich.testsys.model.dao.Dao;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlStatusDaoImpl implements Dao<Status> {
    private static final String STATUS_ID = "status_id";
    private static final String SELECT_STATUS_BY_CRITERIA_QUERY = "SELECT status_id, name FROM testing_system.statuses WHERE ";

    @Override
    public Optional<Status> findById(long id) throws DaoException {
        Optional<Status> optionalTest = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(STATUS_ID, Long.toString(id));
        List<Status> tests = find(criteria);
        if (tests.size() > 0) {
            optionalTest = Optional.of(tests.get(0));
        }
        return optionalTest;
    }

    @Override
    public List<Status> find(Map<String, String> criteria) throws DaoException {
        List<Status> statuses = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        if (con == null) {
            throw new DaoException("Connection is null");
        }
        try {
            DaoUtil daoUtil = new DaoUtil();
            ps = con.prepareStatement(daoUtil.createQueryWithCriteria(SELECT_STATUS_BY_CRITERIA_QUERY, criteria));
            rs = ps.executeQuery();
            while (rs.next()) {
                long subjectId = rs.getLong(1);
                String name = rs.getString(2);
                Status status = new Status(subjectId, name);
                statuses.add(status);
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
        return statuses;
    }

    @Override
    public List<Status> findAll() throws DaoException {
        return null;
    }

    @Override
    public long add(Status status) throws DaoException {
        return 0;
    }

    @Override
    public void update(Status status, Map<String, String> params) throws DaoException {

    }

    @Override
    public void delete(Status status) throws DaoException {

    }
}
