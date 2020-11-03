package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.entity.Question;
import com.kuntsevich.testsys.entity.Status;
import com.kuntsevich.testsys.entity.Subject;
import com.kuntsevich.testsys.entity.Test;
import com.kuntsevich.testsys.model.dao.DaoException;
import com.kuntsevich.testsys.model.dao.TestDao;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;
import com.kuntsevich.testsys.model.dao.pool.DatabaseConnectionPool;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlTestDaoImpl implements TestDao {
    private static final String FIND_ALL_TEST_QUERY = "SELECT test_id, title, subject, description, status, points_to_pass FROM testing_system.tests";
    private static final String TEST_ID = "test_id";

    @Override
    public Optional<Test> findById(long id) throws DaoException {
        Optional<Test> optionalTest = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(TEST_ID, Long.toString(id));
        List<Test> tests = findByCriteria(criteria);
        if (tests.size() > 0) {
            optionalTest = Optional.of(tests.get(0));
        }
        return optionalTest;
    }

    @Override
    public List<Test> findByCriteria(Map<String, String> criteria) throws DaoException {
        List<Test> tests = new ArrayList<>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            DaoUtil daoUtil = new DaoUtil();
            ps = con.prepareStatement(daoUtil.createQueryWithCriteria(FIND_ALL_TEST_QUERY, criteria));
            rs = ps.executeQuery();
            while (rs.next()) {
                long testId = rs.getLong(1);
                String title = rs.getString(2);
                long subjectId = rs.getLong(3);
                Optional<Subject> subjectOptional = DaoFactory.getInstance().getSubjectDao().findById(subjectId);
                Subject subject = subjectOptional.isPresent() ? subjectOptional.get() : new Subject();
                String description = rs.getString(4);
                long statusId = rs.getLong(5);
                Optional<Status> statusOptional = DaoFactory.getInstance().getStatusDao().findById(statusId);
                Status status = statusOptional.isPresent() ? statusOptional.get() : new Status();
                List<Question> questions = DaoFactory.getInstance().getQuestionDao().findByTestId(testId);
                int pointsToPass = rs.getInt(6);
                Test test = new Test(testId, title, subject, description, questions, status, pointsToPass);
                tests.add(test);
            }
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
        return tests;
    }

    @Override
    public List<Test> findAll() throws DaoException {
        List<Test> tests = findByCriteria(new HashMap<>());
        return tests;
    }
}
