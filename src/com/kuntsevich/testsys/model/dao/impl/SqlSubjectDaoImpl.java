package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.entity.Subject;
import com.kuntsevich.testsys.model.dao.exception.DaoException;
import com.kuntsevich.testsys.model.dao.pool.exception.DatabasePoolException;
import com.kuntsevich.testsys.model.dao.SubjectDao;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;
import com.kuntsevich.testsys.model.dao.pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlSubjectDaoImpl implements SubjectDao {
    private static final String SUBJECT_ID = "subject_id";
    private static final String FIND_SUBJECT_BY_CRITERIA_QUERY = "SELECT subject_id, name, description FROM testing_system.subjects WHERE ";
    private static final String FIND_ALL_SUBJECT_QUERY = "SELECT subject_id, name, description FROM testing_system.subjects";

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
            ps = con.prepareStatement(daoUtil.createQueryWithCriteria(FIND_SUBJECT_BY_CRITERIA_QUERY, criteria));
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
}
