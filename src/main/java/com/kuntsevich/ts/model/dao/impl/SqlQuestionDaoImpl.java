package com.kuntsevich.ts.model.dao.impl;

import com.kuntsevich.ts.entity.Answer;
import com.kuntsevich.ts.entity.Question;
import com.kuntsevich.ts.model.dao.DaoException;
import com.kuntsevich.ts.model.dao.QuestionDao;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;
import com.kuntsevich.ts.model.dao.pool.DatabaseConnectionPool;
import com.kuntsevich.ts.model.dao.util.DaoUtil;

import java.sql.*;
import java.util.*;

public class SqlQuestionDaoImpl implements QuestionDao {
    private static final String FIND_ALL_QUESTION_QUERY = "SELECT question_id, text, points, test FROM testing_system.questions";
    private static final String QUESTION_ID = "question_id";
    private static final String TEST_ID = "test";
    private static final String INSERT_QUESTION_QUERY = "INSERT INTO testing_system.questions (text, points, test) VALUES (?, ?, ?)";
    private static final String UPDATE_QUESTION_QUERY = "UPDATE testing_system.questions SET text = ?, points = ?, test = ? WHERE (question_id = ?)";
    private static final String DELETE_QUESTION_QUERY = "DELETE FROM testing_system.questions WHERE (question_id = ?)";

    @Override
    public Optional<Question> findById(long id) throws DaoException {
        Optional<Question> optionalQuestion = Optional.empty();
        Map<String, String> criteria = new HashMap<>();
        criteria.put(QUESTION_ID, Long.toString(id));
        List<Question> questions = findByCriteria(criteria);
        if (questions.size() > 0) {
            optionalQuestion = Optional.of(questions.get(0));
        }
        return optionalQuestion;
    }

    @Override
    public List<Question> findByCriteria(Map<String, String> criteria) throws DaoException {
        List<Question> questions = new ArrayList<>();
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            DaoUtil daoUtil = new DaoUtil();
            ps = con.prepareStatement(daoUtil.createQueryWithCriteria(FIND_ALL_QUESTION_QUERY, criteria));
            rs = ps.executeQuery();
            while (rs.next()) {
                long questionId = rs.getLong(1);
                String text = rs.getString(2);
                int points = rs.getInt(3);
                List<Answer> answers = DaoFactory.getInstance().getAnswerDao().findByQuestionId(questionId);
                Question question = new Question(questionId, text, answers, points);
                questions.add(question);
            }
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
        return questions;
    }

    @Override
    public List<Question> findByTestId(long id) throws DaoException {
        Map<String, String> criteria = new HashMap<>();
        criteria.put(TEST_ID, Long.toString(id));
        return findByCriteria(criteria);
    }

    @Override
    public long add(Question question, long testId) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        long id = -1;
        try {
            ps = con.prepareStatement(INSERT_QUESTION_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, question.getText());
            ps.setInt(2, question.getPoints());
            ps.setLong(3, testId);
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
    public void update(Question question, long testId) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            ps = con.prepareStatement(UPDATE_QUESTION_QUERY);
            ps.setString(1, question.getText());
            ps.setInt(2, question.getPoints());
            ps.setLong(3, testId);
            ps.setLong(4, question.getQuestionId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps);
        }
    }

    @Override
    public void delete(Question question) throws DaoException {
        Connection con;
        PreparedStatement ps = null;
        con = DatabaseConnectionPool.getInstance().getConnection();
        try {
            ps = con.prepareStatement(DELETE_QUESTION_QUERY);
            ps.setLong(1, question.getQuestionId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps);
        }
    }
}
