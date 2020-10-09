package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.entity.Role;
import com.kuntsevich.testsys.exception.DaoException;
import com.kuntsevich.testsys.exception.DatabasePoolException;
import com.kuntsevich.testsys.model.dao.RoleDao;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;
import com.kuntsevich.testsys.pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlRoleDaoImpl implements RoleDao {
    private static final String ROLE_ID = "role_id";
    private static final String FIND_ROLE_BY_CRITERIA_QUERY = "SELECT role_id, name FROM testing_system.roles WHERE ";

    @Override
    public Optional<Role> findById(long id) throws DaoException {
        Optional<Role> optionalRole = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(ROLE_ID, Long.toString(id));
        List<Role> tests = findByCriteria(criteria);
        if (tests.size() > 0) {
            optionalRole = Optional.of(tests.get(0));
        }
        return optionalRole;
    }

    @Override
    public List<Role> findByCriteria(Map<String, String> criteria) throws DaoException {
        List<Role> roles = new ArrayList<>();
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
            DaoUtil.releaseResources(con, ps, rs);
        }
        return roles;
    }
}
