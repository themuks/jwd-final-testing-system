package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.entity.Answer;
import com.kuntsevich.testsys.model.dao.exception.DaoException;
import com.kuntsevich.testsys.model.dao.pool.exception.DatabasePoolException;
import com.kuntsevich.testsys.model.dao.AnswerDao;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;
import com.kuntsevich.testsys.model.dao.pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlAnswerDaoImpl implements AnswerDao {
    private static final String FIND_ANSWER_BY_CRITERIA_QUERY = "SELECT answer_id, text, question, correct FROM testing_system.answers WHERE ";
    private static final String ANSWER_ID = "answer_id";

    @Override
    public Optional<Answer> findById(long id) throws DaoException {
        Optional<Answer> optionalAnswer = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(ANSWER_ID, Long.toString(id));
        List<Answer> answers = findByCriteria(criteria);
        if (answers.size() > 0) {
            optionalAnswer = Optional.of(answers.get(0));
        }
        return optionalAnswer;
    }

    @Override
    public List<Answer> findByCriteria(Map<String, String> criteria) throws DaoException {
        List<Answer> answers = new ArrayList<>();
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
            ps = con.prepareStatement(daoUtil.createQueryWithCriteria(FIND_ANSWER_BY_CRITERIA_QUERY, criteria));
            rs = ps.executeQuery();
            while (rs.next()) {
                long answerId = rs.getLong(1);
                String text = rs.getString(2);
                boolean isCorrect = rs.getBoolean(4);
                Answer answer = new Answer(answerId, text, isCorrect);
                answers.add(answer);
            }
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
        return answers;
    }
}
