package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.connection.DatabaseConnectionPool;
import com.kuntsevich.testsys.entity.Role;
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

public class SqlRoleDaoImpl implements Dao<Role> {
    private static final String ROLE_ID = "role_id";
    private static final String FIND_ROLE_BY_CRITERIA_QUERY = "SELECT role_id, name FROM testing_system.roles WHERE ";

    @Override
    public Optional<Role> findById(long id) throws DaoException {
        Optional<Role> optionalRole = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(ROLE_ID, Long.toString(id));
        List<Role> tests = find(criteria);
        if (tests.size() > 0) {
            optionalRole = Optional.of(tests.get(0));
        }
        return optionalRole;
    }

    @Override
    public List<Role> find(Map<String, String> criteria) throws DaoException {
        List<Role> roles = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        if (con == null) {
            throw new DaoException("Connection is null");
        }
        try {
            DaoUtil daoUtil = new DaoUtil();
            ps = con.prepareStatement(daoUtil.createQueryWithCriteria(FIND_ROLE_BY_CRITERIA_QUERY, criteria));
            rs = ps.executeQuery();
            while (rs.next()) {
                long roleId = rs.getLong(1);
                String name = rs.getString(2);
                Role role = new Role(roleId, name);
                roles.add(role);
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
        return roles;
    }

    @Override
    public List<Role> findAll() throws DaoException {
        return null;
    }

    @Override
    public long add(Role role) throws DaoException {
        return 0;
    }

    @Override
    public void update(Role role, Map<String, String> params) throws DaoException {

    }

    @Override
    public void delete(Role role) throws DaoException {

    }
}
