package com.kuntsevich.ts.model.service.creator;

import com.kuntsevich.ts.entity.Answer;
import com.kuntsevich.ts.entity.Question;
import com.kuntsevich.ts.model.service.exception.CreatorException;
import com.kuntsevich.ts.validator.QuestionValidator;

import java.util.List;

public class QuestionCreator {
    public static Question createQuestion(String text, List<Answer> answers, String points) throws CreatorException {
        if (text == null || answers == null || points == null) {
            throw new CreatorException("Parameters are null");
        }
        QuestionValidator questionValidator = new QuestionValidator();
        if (!questionValidator.isTextValid(text) || !questionValidator.isPointsValid(points)) {
            throw new CreatorException("Invalid parameters");
        }
        int pointsValue = Integer.parseInt(points);
        return new Question(text, answers, pointsValue);
    }
}
