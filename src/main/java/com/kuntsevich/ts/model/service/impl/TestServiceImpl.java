package com.kuntsevich.ts.model.service.impl;

import com.kuntsevich.ts.entity.*;
import com.kuntsevich.ts.model.dao.*;
import com.kuntsevich.ts.model.dao.factory.DaoFactory;
import com.kuntsevich.ts.model.service.TestService;
import com.kuntsevich.ts.model.service.creator.AnswerCreator;
import com.kuntsevich.ts.model.service.creator.QuestionCreator;
import com.kuntsevich.ts.model.service.creator.TestCreator;
import com.kuntsevich.ts.model.service.exception.CreatorException;
import com.kuntsevich.ts.model.service.exception.ServiceException;
import com.kuntsevich.ts.validator.EntityValidator;
import com.kuntsevich.ts.validator.NumberValidator;
import com.kuntsevich.ts.validator.TestValidator;
import org.apache.log4j.Logger;

import java.util.*;

public class TestServiceImpl implements TestService {
    private static final Logger log = Logger.getLogger(TestServiceImpl.class);
    private static final String QUESTION_ID_PREFIX = "q-";
    private static final String ANSWER_ID_PREFIX = "a-";
    private static final String SPACE = " ";
    private static final String POINTS_PREFIX = "p";
    private static final String ONE_POINT = "1";
    private static final String ANSWER_ATTRIBUTE_PREFIX = "c-";
    private static final String STATUS = "Активный";
    private static final int FIRST_ELEMENT = 0;

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
        if (!EntityValidator.isIdValid(testId)) {
            throw new ServiceException("Test id is invalid");
        }
        if (!EntityValidator.isIdValid(userId)) {
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
            long userIdLongValue = Long.parseLong(userId);
            UserDao userDao = DaoFactory.getInstance().getUserDao();
            User user;
            try {
                Optional<User> optionalUser = userDao.findById(userIdLongValue);
                user = optionalUser.orElseGet(User::new);
            } catch (DaoException e) {
                throw new ServiceException("Error finding user by id", e);
            }
            boolean isTestPassed = points >= test.getPointsToPass();
            Result result = new Result(user, test, points, correctAnswers, totalPoints, isTestPassed);
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

    @Override
    public boolean createTest(String title, String subject, String description, String pointsToPass, Map<String, String[]> questionsParameters, List<String> answersAttributes) throws ServiceException {
        if (title == null
                || subject == null
                || description == null
                || pointsToPass == null
                || questionsParameters == null
                || answersAttributes == null) {
            throw new ServiceException("Parameters are null");
        }
        if (!TestValidator.isTitleValid(title)
                || !TestValidator.isDescriptionValid(description)
                || !TestValidator.isSubjectNameValid(subject)
                || !TestValidator.isPointsToPassValid(pointsToPass)) {
            throw new ServiceException("Invalid parameters");
        }
        Set<String> keys = questionsParameters.keySet();
        QuestionDao questionDao = DaoFactory.getInstance().getQuestionDao();
        AnswerDao answerDao = DaoFactory.getInstance().getAnswerDao();
        List<Question> questions = new ArrayList<>();
        int i = 1;
        while (keys.contains(QUESTION_ID_PREFIX + i)) {
            String questionText = questionsParameters.get(QUESTION_ID_PREFIX + i)[FIRST_ELEMENT];
            List<Answer> answers = new ArrayList<>();
            String answerKey = QUESTION_ID_PREFIX + i + SPACE + ANSWER_ID_PREFIX;
            // We had always 4 questionsParameters per question
            for (int j = 1; j <= 4; j++) {
                if (keys.contains(answerKey + j)) {
                    String answerText = questionsParameters.get(answerKey + j)[FIRST_ELEMENT];
                    boolean isCorrect = false;
                    if (answersAttributes.contains(QUESTION_ID_PREFIX + i + SPACE + ANSWER_ATTRIBUTE_PREFIX + j)) {
                        isCorrect = true;
                    }
                    Answer answer;
                    try {
                        answer = AnswerCreator.createAnswer(answerText, Boolean.toString(isCorrect));
                    } catch (CreatorException e) {
                        throw new ServiceException("Error while creating answer", e);
                    }
                    answers.add(answer);
                }
            }
            String questionPointsKey = QUESTION_ID_PREFIX + i + SPACE + POINTS_PREFIX;
            String points = ONE_POINT;
            if (keys.contains(questionPointsKey)) {
                points = questionsParameters.get(questionPointsKey)[FIRST_ELEMENT];
            }
            Question question;
            try {
                question = QuestionCreator.createQuestion(questionText, answers, points);
            } catch (CreatorException e) {
                throw new ServiceException("Error while creating question", e);
            }
            questions.add(question);
            i++;
        }
        TestDao testDao = DaoFactory.getInstance().getTestDao();
        Test test;
        try {
            test = TestCreator.createTest(title, subject, description, questions, STATUS, pointsToPass);
        } catch (CreatorException e) {
            throw new ServiceException("Error while creating test", e);
        }
        long testId;
        try {
            testId = testDao.add(test);
            test.setTestId(testId);
        } catch (DaoException e) {
            throw new ServiceException("Error while adding test", e);
        }
        for (var question : questions) {
            long questionId;
            try {
                questionId = questionDao.add(question, testId);
                question.setQuestionId(questionId);
            } catch (DaoException e) {
                throw new ServiceException("Error while adding question", e);
            }
            List<Answer> answers = question.getAnswers();
            for (var answer : answers) {
                try {
                    long answerId = answerDao.add(answer, questionId);
                    answer.setAnswerId(answerId);
                } catch (DaoException e) {
                    throw new ServiceException("Error while adding answer", e);
                }
            }
        }
        return true;
    }

    @Override
    public boolean deleteTest(String testId) throws ServiceException {
        if (testId == null) {
            return false;
        }
        if (!EntityValidator.isIdValid(testId)) {
            return false;
        }
        TestDao testDao = DaoFactory.getInstance().getTestDao();
        Test test;
        try {
            Optional<Test> optionalTest = testDao.findById(Long.parseLong(testId));
            if (optionalTest.isPresent()) {
                test = optionalTest.get();
            } else {
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException("Error while finding test by id", e);
        }
        QuestionDao questionDao = DaoFactory.getInstance().getQuestionDao();
        AnswerDao answerDao = DaoFactory.getInstance().getAnswerDao();
        List<Question> questions = test.getQuestions();
        for (var question : questions) {
            List<Answer> answers = question.getAnswers();
            for (var answer : answers) {
                try {
                    answerDao.delete(answer);
                } catch (DaoException e) {
                    throw new ServiceException("Error while deleting answer", e);
                }
            }
            try {
                questionDao.delete(question);
            } catch (DaoException e) {
                throw new ServiceException("Error while deleting question", e);
            }
        }
        ResultDao resultDao = DaoFactory.getInstance().getResultDao();
        List<Result> results;
        try {
            results = resultDao.findByTestId(Long.parseLong(testId));
        } catch (DaoException e) {
            throw new ServiceException("Error while finding results by test id", e);
        }
        for (var result : results) {
            try {
                resultDao.delete(result);
            } catch (DaoException e) {
                throw new ServiceException("Error while deleting result", e);
            }
        }
        try {
            testDao.delete(test);
        } catch (DaoException e) {
            throw new ServiceException("Error while deleting test", e);
        }
        return true;
    }

    @Override
    public int findPageCount(String recordsPerPage) throws ServiceException {
        if (recordsPerPage == null) {
            throw new ServiceException("Parameter is null");
        }
        if (!NumberValidator.isIntegerValid(recordsPerPage)) {
            throw new ServiceException("Page is invalid");
        }
        TestDao testDao = DaoFactory.getInstance().getTestDao();
        try {
            int recordsCount = testDao.findCount();
            int recordsPerPageInt = Integer.parseInt(recordsPerPage);
            int pageCount = recordsCount / recordsPerPageInt;
            pageCount = recordsCount % recordsPerPageInt > 0 ? pageCount + 1 : pageCount;
            return pageCount;
        } catch (DaoException e) {
            throw new ServiceException("Error while finding test count");
        }
    }

    @Override
    public List<Test> findPageTests(String page, String recordsPerPage) throws ServiceException {
        if (page == null || recordsPerPage == null) {
            throw new ServiceException("Parameters are null");
        }
        if (!NumberValidator.isIntegerValid(page) || !NumberValidator.isIntegerValid(recordsPerPage)) {
            throw new ServiceException("Parameters are invalid");
        }
        TestDao testDao = DaoFactory.getInstance().getTestDao();
        int rpp = Integer.parseInt(recordsPerPage);
        try {
            return testDao.findWithLimits(rpp * (Integer.parseInt(page) - 1), rpp);
        } catch (DaoException e) {
            throw new ServiceException("Error while finding tests with limits");
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
