package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.entity.Answer;
import com.kuntsevich.testsys.model.dao.AnswerDao;
import com.kuntsevich.testsys.model.dao.DaoException;
import com.kuntsevich.testsys.model.dao.pool.DatabaseConnectionPool;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlAnswerDaoImpl implements AnswerDao {
    private static final String FIND_ALL_ANSWER_QUERY = "SELECT answer_id, text, question, correct FROM testing_system.answers";
    private static final String ANSWER_ID = "answer_id";
    private static final String QUESTION = "question";

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
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            DaoUtil daoUtil = new DaoUtil();
            ps = con.prepareStatement(daoUtil.createQueryWithCriteria(FIND_ALL_ANSWER_QUERY, criteria));
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

    @Override
    public List<Answer> findByQuestionId(long id) throws DaoException {
        Map<String, String> criteria = new HashMap<>();
        criteria.put(QUESTION, Long.toString(id));
        List<Answer> answers = findByCriteria(criteria);
        return answers;
    }
}
