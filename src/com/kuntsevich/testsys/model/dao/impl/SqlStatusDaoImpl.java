package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.entity.Status;
import com.kuntsevich.testsys.model.dao.exception.DaoException;
import com.kuntsevich.testsys.model.dao.pool.exception.DatabasePoolException;
import com.kuntsevich.testsys.model.dao.StatusDao;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;
import com.kuntsevich.testsys.model.dao.pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlStatusDaoImpl implements StatusDao {
    private static final String STATUS_ID = "status_id";
    private static final String FIND_STATUS_BY_CRITERIA_QUERY = "SELECT status_id, name FROM testing_system.statuses WHERE ";

    @Override
    public Optional<Status> findById(long id) throws DaoException {
        Optional<Status> optionalStatus = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(STATUS_ID, Long.toString(id));
        List<Status> statuses = findByCriteria(criteria);
        if (statuses.size() > 0) {
            optionalStatus = Optional.of(statuses.get(0));
        }
        return optionalStatus;
    }

    @Override
    public List<Status> findByCriteria(Map<String, String> criteria) throws DaoException {
        List<Status> statuses = new ArrayList<>();
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
            ps = con.prepareStatement(daoUtil.createQueryWithCriteria(FIND_STATUS_BY_CRITERIA_QUERY, criteria));
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
            DaoUtil.releaseResources(con, ps, rs);
        }
        return statuses;
    }
}
