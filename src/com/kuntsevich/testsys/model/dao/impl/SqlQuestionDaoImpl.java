package com.kuntsevich.testsys.model.dao.impl;

import com.kuntsevich.testsys.entity.Answer;
import com.kuntsevich.testsys.entity.Question;
import com.kuntsevich.testsys.entity.Subject;
import com.kuntsevich.testsys.model.dao.DaoException;
import com.kuntsevich.testsys.model.dao.QuestionDao;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;
import com.kuntsevich.testsys.model.dao.pool.DatabaseConnectionPool;
import com.kuntsevich.testsys.model.dao.util.DaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlQuestionDaoImpl implements QuestionDao {
    private static final String FIND_ALL_QUESTION_QUERY = "SELECT question_id, text, subject, points, test FROM testing_system.questions";
    private static final String QUESTION_ID = "question_id";
    private static final String TEST = "test";

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
                long subjectId = rs.getLong(3);
                Optional<Subject> subjectOptional = DaoFactory.getInstance().getSubjectDao().findById(subjectId);
                Subject subject = subjectOptional.isPresent() ? subjectOptional.get() : new Subject();
                int points = rs.getInt(4);
                List<Answer> answers = DaoFactory.getInstance().getAnswerDao().findByQuestionId(questionId);
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

    @Override
    public List<Question> findByTestId(long id) throws DaoException {
        Map<String, String> criteria = new HashMap<>();
        criteria.put(TEST, Long.toString(id));
        List<Question> questions = findByCriteria(criteria);
        return questions;
    }
}
