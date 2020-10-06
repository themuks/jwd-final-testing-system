package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.connection.DatabaseConnectionPool;
import com.kuntsevich.testsys.entity.Subject;
import com.kuntsevich.testsys.exception.DaoException;
import com.kuntsevich.testsys.exception.DatabasePoolException;
import com.kuntsevich.testsys.model.dao.SubjectDao;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlSubjectDaoImpl implements SubjectDao {
    private static final String SUBJECT_ID = "subject_id";
    private static final String SELECT_SUBJECT_BY_CRITERIA_QUERY = "SELECT subject_id, name, description FROM testing_system.subjects WHERE ";

    @Override
    public Optional<Subject> findById(long id) throws DaoException {
        Optional<Subject> optionalTest = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(SUBJECT_ID, Long.toString(id));
        List<Subject> tests = findByCriteria(criteria);
        if (tests.size() > 0) {
            optionalTest = Optional.of(tests.get(0));
        }
        return optionalTest;
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
            ps = con.prepareStatement(daoUtil.createQueryWithCriteria(SELECT_SUBJECT_BY_CRITERIA_QUERY, criteria));
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
