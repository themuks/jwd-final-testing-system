package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.entity.Answer;
import com.kuntsevich.testsys.entity.Question;
import com.kuntsevich.testsys.entity.Subject;
import com.kuntsevich.testsys.model.dao.exception.DaoException;
import com.kuntsevich.testsys.model.dao.pool.exception.DatabasePoolException;
import com.kuntsevich.testsys.model.dao.QuestionDao;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;
import com.kuntsevich.testsys.model.dao.pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlQuestionDaoImpl implements QuestionDao {
    private static final String FIND_QUESTION_BY_CRITERIA_QUERY = "SELECT question_id, text, subject, points, test FROM testing_system.questions WHERE ";
    private static final String CRITERIA_ANSWER_QUESTION = "question";
    private static final String QUESTION_ID = "question_id";

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
            ps = con.prepareStatement(daoUtil.createQueryWithCriteria(FIND_QUESTION_BY_CRITERIA_QUERY, criteria));
            rs = ps.executeQuery();
            while (rs.next()) {
                long questionId = rs.getLong(1);
                String text = rs.getString(2);
                long subjectId = rs.getLong(3);
                Optional<Subject> subjectOptional = DaoFactory.getInstance().getSubjectDao().findById(subjectId);
                Subject subject = subjectOptional.isPresent() ? subjectOptional.get() : new Subject();
                int points = rs.getInt(4);
                Map<String, String> answerCriteria = new HashMap<>();
                answerCriteria.put(CRITERIA_ANSWER_QUESTION, Long.toString(questionId));
                List<Answer> answers = DaoFactory.getInstance().getAnswerDao().findByCriteria(answerCriteria);
                Question question = new Question(questionId, text, subject, answers, points);
                questions.add(question);
            }
        } catch (SQLException e) {
            throw new DaoException("Error executing query", e);
        } finally {
            DaoUtil.releaseResources(con, ps, rs);
        }
        return questions;
    }
}
