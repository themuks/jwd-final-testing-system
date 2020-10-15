package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.entity.Role;
import com.kuntsevich.testsys.entity.Status;
import com.kuntsevich.testsys.entity.User;
import com.kuntsevich.testsys.model.dao.exception.DaoException;
import com.kuntsevich.testsys.model.dao.pool.exception.DatabasePoolException;
import com.kuntsevich.testsys.model.dao.UserDao;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;
import com.kuntsevich.testsys.model.dao.pool.DatabaseConnectionPool;

import java.sql.*;
import java.util.*;

public class SqlUserDaoImpl implements UserDao {
    private static final String USER_ID = "user_id";
    private static final String FIND_ALL_USER_QUERY = "SELECT user_id, username, name, surname, email_hash, password_hash, user_hash, role, status FROM testing_system.users";
    private static final String FIND_USER_BY_CRITERIA_QUERY = "SELECT user_id, username, name, surname, email_hash, password_hash, user_hash, role, status FROM testing_system.users WHERE ";
    private static final String INSERT_USER_QUERY = "INSERT INTO `testing_system`.`users` (`username`, `name`, `surname`, `email_hash`, `password_hash`, `user_hash`, `role`, `status`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_USER_QUERY = "DELETE FROM `testing_system`.`users` WHERE (`user_id` = ?)";

    @Override
    public Optional<User> findById(long id) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(USER_ID, Long.toString(id));
        List<User> users = findByCriteria(criteria);
        if (users.size() > 0) {
            optionalUser = Optional.of(users.get(0));
        }
        return optionalUser;
    }

    @Override
    public List<User> findByCriteria(Map<String, String> criteria) throws DaoException {
        List<User> users = new ArrayList<>();
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
            query = daoUtil.createQueryWithCriteria(FIND_USER_BY_CRITERIA_QUERY, criteria);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                long userId = rs.getLong(1);
                String username = rs.getString(2);
                String name = rs.getString(3);
                String surname = rs.getString(4);
                String emailHash = rs.getString(5);
                String passwordHash = rs.getString(6);
                String userHash = rs.getString(7);
                long roleId = rs.getLong(8);
                Optional<Role> roleOptional = DaoFactory.getInstance().getRoleDao().findById(roleId);
                Role role = roleOptional.isPresent() ? roleOptional.get() : new Role();
                long statusId = rs.getLong(9);
                Optional<Status> statusOptional = DaoFactory.getInstance().getStatusDao().findById(statusId);
                Status status = statusOptional.isPresent() ? statusOptional.get() : new Status();
                User user = new User(userId, username, name, surname, emailHash, passwordHash, userHash, role, status);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Error executing query " + query, e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
        return users;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
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
            ps = con.prepareStatement(FIND_ALL_USER_QUERY);
            rs = ps.executeQuery();
            while (rs.next()) {
                long userId = rs.getLong(1);
                String username = rs.getString(2);
                String name = rs.getString(3);
                String surname = rs.getString(4);
                String emailHash = rs.getString(5);
                String passwordHash = rs.getString(6);
                String userHash = rs.getString(7);
                long roleId = rs.getLong(8);
                Optional<Role> roleOptional = DaoFactory.getInstance().getRoleDao().findById(roleId);
                Role role = roleOptional.isPresent() ? roleOptional.get() : new Role();
                long statusId = rs.getLong(9);
                Optional<Status> statusOptional = DaoFactory.getInstance().getStatusDao().findById(statusId);
                Status status = statusOptional.isPresent() ? statusOptional.get() : new Status();
                User user = new User(userId, username, name, surname, emailHash, passwordHash, userHash, role, status);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
        return users;
    }

    @Override
    public long add(User user) throws DaoException {
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
            ps = con.prepareStatement(INSERT_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getName());
            ps.setString(3, user.getSurname());
            ps.setString(4, user.getEmailHash());
            ps.setString(5, user.getPasswordHash());
            ps.setString(6, user.getUserHash());
            ps.setLong(7, user.getRole().getRoleId());
            ps.setLong(8, user.getStatus().getStatusId());
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
    public void update(User user) throws DaoException {
        delete(user);
        add(user);
    }

    @Override
    public void delete(User user) throws DaoException {
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
            ps = con.prepareStatement(DELETE_USER_QUERY);
            ps.setLong(1, user.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps);
        }
    }
}
