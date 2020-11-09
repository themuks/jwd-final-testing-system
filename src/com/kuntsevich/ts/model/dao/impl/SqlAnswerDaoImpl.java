package com.kuntsevich.ts.model.dao.impl;

import com.kuntsevich.ts.entity.Answer;
import com.kuntsevich.ts.model.dao.AnswerDao;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.pool.DatabaseConnectionPool;
import com.kuntsevich.ts.model.dao.util.DaoUtil;

import java.sql.*;
import java.util.*;

public class SqlAnswerDaoImpl implements AnswerDao {
    private static final String FIND_ALL_ANSWER_QUERY = "SELECT answer_id, text, question, correct FROM testing_system.answers";
    private static final String ANSWER_ID = "answer_id";
    private static final String QUESTION = "question";
    private static final String INSERT_ANSWER_QUERY = "INSERT INTO testing_system.answers (text, question, correct) VALUES (?, ?, ?)";
    private static final String UPDATE_ANSWER_QUERY = "UPDATE testing_system.answers SET text = ?, question = ?, correct = ? WHERE (answer_id = ?)";
    private static final String DELETE_ANSWER_QUERY = "DELETE FROM testing_system.answers WHERE (answer_id = ?)";

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
                boolean isCorrect = rs.getBoolean(3);
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
        return findByCriteria(criteria);
    }

    @Override
    public long add(Answer answer, long questionId) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        long id = -1;
        try {
            ps = con.prepareStatement(INSERT_ANSWER_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, answer.getText());
            ps.setLong(2, questionId);
            ps.setBoolean(3, answer.isCorrect());
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
    public void update(Answer answer, long questionId) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            ps = con.prepareStatement(UPDATE_ANSWER_QUERY);
            ps.setString(1, answer.getText());
            ps.setLong(2, questionId);
            ps.setBoolean(3, answer.isCorrect());
            ps.setLong(4, answer.getAnswerId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps);
        }
    }

    @Override
    public void delete(Answer answer) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            ps = con.prepareStatement(DELETE_ANSWER_QUERY);
            ps.setLong(1, answer.getAnswerId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps);
        }
    }
}
