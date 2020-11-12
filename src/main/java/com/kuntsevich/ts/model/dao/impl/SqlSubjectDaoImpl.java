package com.kuntsevich.ts.model.dao.impl;

import com.kuntsevich.ts.entity.Subject;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.SubjectDao;
import com.kuntsevich.ts.model.dao.pool.DatabaseConnectionPool;
import com.kuntsevich.ts.model.dao.util.DaoUtil;

import java.sql.*;
import java.util.*;

public class SqlSubjectDaoImpl implements SubjectDao {
    private static final String SUBJECT_ID = "subject_id";
    private static final String FIND_ALL_SUBJECT_QUERY = "SELECT subject_id, name, description FROM testing_system.subjects";
    private static final String INSERT_SUBJECT_QUERY = "INSERT INTO  testing_system.subjects (name, description) VALUES (?, ?)";
    private static final String DELETE_SUBJECT_QUERY = "DELETE FROM testing_system.subjects WHERE (subject_id = ?)";
    private static final String UPDATE_SUBJECT_QUERY = "UPDATE testing_system.subjects SET name = ?, description = ? WHERE (subject_id = ?)";
    private static final String NAME = "name";
    private static final int FIRST_ELEMENT = 0;

    @Override
    public Optional<Subject> findById(long id) throws DaoException {
        Optional<Subject> optionalSubject = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(SUBJECT_ID, Long.toString(id));
        List<Subject> subjects = findByCriteria(criteria);
        if (subjects.size() > 0) {
            optionalSubject = Optional.of(subjects.get(0));
        }
        return optionalSubject;
    }

    @Override
    public List<Subject> findByCriteria(Map<String, String> criteria) throws DaoException {
        List<Subject> subjects = new ArrayList<>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            DaoUtil daoUtil = new DaoUtil();
            ps = con.prepareStatement(daoUtil.createQueryWithCriteria(FIND_ALL_SUBJECT_QUERY, criteria));
            rs = ps.executeQuery();
            while (rs.next()) {
                long subjectId = rs.getLong(1);
                String name = rs.getString(2);
                String description = rs.getString(3);
                Subject subject = new Subject(subjectId, name, description);
                subjects.add(subject);
            }
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
        return subjects;
    }

    @Override
    public List<Subject> findAll() throws DaoException {
        List<Subject> subjects = new ArrayList<>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        if (con == null) {
            throw new DaoException("Connection is null");
        }
        try {
            ps = con.prepareStatement(FIND_ALL_SUBJECT_QUERY);
            rs = ps.executeQuery();
            while (rs.next()) {
                long subjectId = rs.getLong(1);
                String name = rs.getString(2);
                String description = rs.getString(3);
                Subject subject = new Subject(subjectId, name, description);
                subjects.add(subject);
            }
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
        return subjects;
    }

    @Override
    public Optional<Subject> findByName(String name) throws DaoException {
        Optional<Subject> optionalSubject = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(NAME, name);
        List<Subject> subjects = findByCriteria(criteria);
        if (subjects.size() > 0) {
            optionalSubject = Optional.of(subjects.get(FIRST_ELEMENT));
        }
        return optionalSubject;
    }

    @Override
    public long add(Subject subject) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        long id = -1;
        try {
            ps = con.prepareStatement(INSERT_SUBJECT_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, subject.getName());
            ps.setString(2, subject.getDescription());
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
    public void update(Subject subject) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            ps = con.prepareStatement(UPDATE_SUBJECT_QUERY);
            ps.setString(1, subject.getName());
            ps.setString(2, subject.getDescription());
            ps.setLong(3, subject.getSubjectId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps);
        }
    }

    @Override
    public void delete(Subject subject) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            ps = con.prepareStatement(DELETE_SUBJECT_QUERY);
            ps.setLong(1, subject.getSubjectId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps);
        }
    }
}
