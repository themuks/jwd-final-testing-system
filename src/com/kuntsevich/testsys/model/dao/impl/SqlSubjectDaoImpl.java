package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.entity.Subject;
import com.kuntsevich.testsys.model.dao.DaoException;
import com.kuntsevich.testsys.model.dao.SubjectDao;
import com.kuntsevich.testsys.model.dao.pool.DatabaseConnectionPool;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlSubjectDaoImpl implements SubjectDao {
    private static final String SUBJECT_ID = "subject_id";
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
}
