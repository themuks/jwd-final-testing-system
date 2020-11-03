package com.kuntsevich.testsys.model.service.impl;

import com.kuntsevich.testsys.entity.*;
import com.kuntsevich.testsys.model.dao.ResultDao;
import com.kuntsevich.testsys.model.dao.DaoException;
import com.kuntsevich.testsys.model.dao.factory.DaoFactory;
import com.kuntsevich.testsys.model.service.TestService;
import com.kuntsevich.testsys.model.service.exception.ServiceException;
import com.kuntsevich.testsys.model.service.validator.TestValidator;
import com.kuntsevich.testsys.model.service.validator.UserValidator;
import org.apache.log4j.Logger;

import java.util.*;

public class TestServiceImpl implements TestService {
    private static final Logger log = Logger.getLogger(TestServiceImpl.class);
    private static final String QUESTION_ID_PREFIX = "q-";
    private static final String ANSWER_ID_PREFIX = "a-";

    @Override
    public List<Test> findAll() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        List<Test> tests;
        try {
            tests = daoFactory.getTestDao().findAll();
        } catch (DaoException e) {
            throw new ServiceException("Error when executing find all method", e);
        }
        return tests;
    }

    // TODO: 15.10.2020 Refactor this method
    @Override
    public Result submitTest(String testId, String userId, Map<String, String[]> answers) throws ServiceException {
        if (testId == null || userId == null || answers == null) {
            throw new ServiceException("Parameters are null");
        }
        TestValidator testValidator = new TestValidator();
        if (!testValidator.isTestIdValid(testId)) {
            throw new ServiceException("Test id is invalid");
        }
        UserValidator userValidator = new UserValidator();
        if (!userValidator.isIdValid(userId)) {
            throw new ServiceException("User id is invalid");
        }
        long id = Long.parseLong(testId);
        Optional<Test> optionalTest;
        try {
            optionalTest = DaoFactory.getInstance().getTestDao().findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Error finding optionalTest by id");
        }

        if (optionalTest.isPresent()) {
            Map<Long, List<Answer>> answerMap = new HashMap<>();
            List<Long> questionKeys = new ArrayList<>();
            Test test = optionalTest.get();
            List<Question> questions = test.getQuestions();
            for (Question question : questions) {
                questionKeys.add(question.getQuestionId());
            }
            for (Long questionKey : questionKeys) {
                List<Answer> questionAnswers = new ArrayList<>();
                Set<String> answerKeys = answers.keySet();
                for (String answerKey : answerKeys) {
                    if (answerKey.contains(QUESTION_ID_PREFIX + questionKey)) {
                        String answerIdSubstring = answerKey.substring(answerKey.indexOf(ANSWER_ID_PREFIX) + ANSWER_ID_PREFIX.length());
                        Optional<Answer> optionalAnswer;
                        try {
                            optionalAnswer = DaoFactory.getInstance().getAnswerDao().findById(Long.parseLong(answerIdSubstring));
                        } catch (DaoException e) {
                            throw new ServiceException("Error finding answer by id", e);
                        }
                        if (optionalAnswer.isPresent()) {
                            questionAnswers.add(optionalAnswer.get());
                        } else {
                            log.warn("Answer id = " + answerIdSubstring);
                            throw new ServiceException("Answer not found");
                        }
                    }
                }
                answerMap.put(questionKey, questionAnswers);
            }

            int points = 0;
            int totalPoints = 0;
            int correctAnswers = 0;
            for (Question question : questions) {
                long questionId = question.getQuestionId();
                int questionPoints = question.getPoints();
                if (isQuestionAnswersCorrect(question, answerMap.get(questionId))) {
                    points += questionPoints;
                    correctAnswers++;
                }
                totalPoints += questionPoints;
            }

            ResultDao resultDao = DaoFactory.getInstance().getResultDao();
            // FIXME: 15.10.2020 Create Result object with right User reference
            User tempUser = new User();
            long userIdLongValue = Long.parseLong(userId);
            tempUser.setUserId(userIdLongValue);
            boolean isTestPassed = points >= test.getPointsToPass();
            Result result = new Result(tempUser, test, points, correctAnswers, totalPoints, isTestPassed);
            try {
                resultDao.add(result);
            } catch (DaoException e) {
                throw new ServiceException("Error adding result to the database", e);
            }
            return result;
        } else {
            throw new ServiceException("Test not found");
        }
    }

    private boolean isQuestionAnswersCorrect(Question question, List<Answer> answers) {
        List<Answer> questionAnswers = question.getAnswers();
        List<Answer> correctAnswers = new ArrayList<>();
        for (Answer answer : questionAnswers) {
            if (answer.isCorrect()) {
                correctAnswers.add(answer);
            }
        }
        boolean result = false;
        if (answers.equals(correctAnswers)) {
            result = true;
        }
        return result;
    }
}
