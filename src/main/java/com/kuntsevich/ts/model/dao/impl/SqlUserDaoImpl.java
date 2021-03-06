package com.kuntsevich.ts.model.dao.impl;

import com.kuntsevich.ts.entity.Role;
import com.kuntsevich.ts.entity.Status;
import com.kuntsevich.ts.entity.User;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.UserDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;
import com.kuntsevich.ts.model.dao.pool.DatabaseConnectionPool;
import com.kuntsevich.ts.model.dao.util.DaoUtil;

import java.sql.*;
import java.util.*;

public class SqlUserDaoImpl implements UserDao {
    private static final String USER_ID = "user_id";
    private static final String FIND_ALL_USER_QUERY = "SELECT user_id, username, name, surname, email, password_hash, user_hash, role, status FROM testing_system.users";
    private static final String INSERT_USER_QUERY = "INSERT INTO testing_system.users (username, name, surname, email, password_hash, user_hash, role, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_USER_QUERY = "DELETE FROM testing_system.users WHERE (user_id = ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE testing_system.users SET username = ?, name = ?, surname = ?, email = ?, password_hash = ?, user_hash = ?, role = ?, status = ? WHERE (user_id = ?)";
    private static final String EMPTY_STRING = "";
    private static final String EMAIL = "email";
    private static final int FIRST_ELEMENT_INDEX = 0;
    private static final String PASSWORD_HASH = "password_hash";
    private static final String USER_HASH = "user_hash";
    private static final String FIND_USER_WITH_LIMITS_QUERY = "SELECT user_id, username, name, surname, email, password_hash, user_hash, role, status FROM testing_system.users ORDER BY user_id DESC LIMIT ? OFFSET ?";
    private static final String FIND_COUNT_OF_USERS_QUERY = "SELECT count(*) FROM testing_system.users";

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
        con = DatabaseConnectionPool.getInstance().getConnection();
        String query = EMPTY_STRING;
        try {
            DaoUtil daoUtil = new DaoUtil();
            query = daoUtil.createQueryWithCriteria(FIND_ALL_USER_QUERY, criteria);
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                long userId = rs.getLong(1);
                String username = rs.getString(2);
                String name = rs.getString(3);
                String surname = rs.getString(4);
                String email = rs.getString(5);
                String passwordHash = rs.getString(6);
                String userHash = rs.getString(7);
                long roleId = rs.getLong(8);
                Optional<Role> roleOptional = DaoFactory.getInstance().getRoleDao().findById(roleId);
                Role role = roleOptional.orElseGet(Role::new);
                long statusId = rs.getLong(9);
                Optional<Status> statusOptional = DaoFactory.getInstance().getStatusDao().findById(statusId);
                Status status = statusOptional.orElseGet(Status::new);
                User user = new User(userId, username, name, surname, email, passwordHash, userHash, role, status);
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
        return findByCriteria(new HashMap<>());
    }

    @Override
    public long add(User user) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        long id = -1;
        try {
            ps = con.prepareStatement(INSERT_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getName());
            ps.setString(3, user.getSurname());
            ps.setString(4, user.getEmail());
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
        Connection con;
        PreparedStatement ps = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            ps = con.prepareStatement(UPDATE_USER_QUERY);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getName());
            ps.setString(3, user.getSurname());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPasswordHash());
            ps.setString(6, user.getUserHash());
            ps.setLong(7, user.getRole().getRoleId());
            ps.setLong(8, user.getStatus().getStatusId());
            ps.setLong(9, user.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps);
        }
    }

    @Override
    public void delete(User user) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
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

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        Map<String, String> criteria = new HashMap<>();
        criteria.put(EMAIL, email);
        List<User> users = findByCriteria(criteria);
        Optional<User> optionalUser = Optional.empty();
        if (users.size() > 0) {
            optionalUser = Optional.of(users.get(FIRST_ELEMENT_INDEX));
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findByEmailAndPasswordHash(String email, String passwordHash) throws DaoException {
        Map<String, String> criteria = new HashMap<>();
        criteria.put(PASSWORD_HASH, passwordHash);
        criteria.put(EMAIL, email);
        List<User> users = findByCriteria(criteria);
        Optional<User> optionalUser = Optional.empty();
        if (users.size() > 0) {
            optionalUser = Optional.of(users.get(FIRST_ELEMENT_INDEX));
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findByUserIdAndPasswordHash(long userId, String passwordHash) throws DaoException {
        Map<String, String> criteria = new HashMap<>();
        criteria.put(USER_ID, Long.toString(userId));
        criteria.put(PASSWORD_HASH, passwordHash);
        List<User> users = findByCriteria(criteria);
        Optional<User> optionalUser = Optional.empty();
        if (users.size() > 0) {
            optionalUser = Optional.of(users.get(FIRST_ELEMENT_INDEX));
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findByEmailAndUserHash(String userHash, String email) throws DaoException {
        Map<String, String> criteria = new HashMap<>();
        criteria.put(USER_HASH, userHash);
        criteria.put(EMAIL, email);
        List<User> users = findByCriteria(criteria);
        Optional<User> optionalUser = Optional.empty();
        if (users.size() > 0) {
            optionalUser = Optional.of(users.get(FIRST_ELEMENT_INDEX));
        }
        return optionalUser;
    }

    @Override
    public List<User> findWithLimits(int offset, int limit) throws DaoException {
        List<User> users = new ArrayList<>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        String query = EMPTY_STRING;
        try {
            query = FIND_USER_WITH_LIMITS_QUERY;
            ps = con.prepareStatement(query);
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            rs = ps.executeQuery();
            while (rs.next()) {
                long userId = rs.getLong(1);
                String username = rs.getString(2);
                String name = rs.getString(3);
                String surname = rs.getString(4);
                String email = rs.getString(5);
                String passwordHash = rs.getString(6);
                String userHash = rs.getString(7);
                long roleId = rs.getLong(8);
                Optional<Role> roleOptional = DaoFactory.getInstance().getRoleDao().findById(roleId);
                Role role = roleOptional.orElseGet(Role::new);
                long statusId = rs.getLong(9);
                Optional<Status> statusOptional = DaoFactory.getInstance().getStatusDao().findById(statusId);
                Status status = statusOptional.orElseGet(Status::new);
                User user = new User(userId, username, name, surname, email, passwordHash, userHash, role, status);
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
    public int findCount() throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        String query = EMPTY_STRING;
        try {
            query = FIND_COUNT_OF_USERS_QUERY;
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
