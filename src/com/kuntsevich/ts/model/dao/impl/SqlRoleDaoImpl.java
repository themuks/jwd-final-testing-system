package com.kuntsevich.ts.model.dao.impl;

import com.kuntsevich.ts.entity.Role;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.RoleDao;
import com.kuntsevich.ts.model.dao.pool.DatabaseConnectionPool;
import com.kuntsevich.ts.model.dao.util.DaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlRoleDaoImpl implements RoleDao {
    private static final String ROLE_ID = "role_id";
    private static final String FIND_ALL_ROLE_QUERY = "SELECT role_id, name FROM testing_system.roles";
    private static final String NAME = "name";
    private static final int FIRST_ELEMENT_INDEX = 0;

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
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            DaoUtil daoUtil = new DaoUtil();
            ps = con.prepareStatement(daoUtil.createQueryWithCriteria(FIND_ALL_ROLE_QUERY, criteria));
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

    @Override
    public Optional<Role> findByName(String name) throws DaoException {
        Optional<Role> optionalRole = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(NAME, name);
        List<Role> tests = findByCriteria(criteria);
        if (tests.size() > 0) {
            optionalRole = Optional.of(tests.get(FIRST_ELEMENT_INDEX));
        }
        return optionalRole;
    }
}
