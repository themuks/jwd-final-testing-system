package com.kuntsevich.ts.model.service.creator;

import com.kuntsevich.ts.entity.Answer;
import com.kuntsevich.ts.model.service.exception.CreatorException;
import com.kuntsevich.ts.validator.AnswerValidator;

public class AnswerCreator {
    public static Answer createAnswer(String text, String isCorrect) throws CreatorException {
        if (text == null || isCorrect == null) {
            throw new CreatorException("Text is null");
        }
        AnswerValidator answerValidator = new AnswerValidator();
        if (!answerValidator.isTextValid(text)) {
            throw new CreatorException("Invalid text value");
        }
        boolean isCorrectValue = Boolean.parseBoolean(isCorrect);
        return new Answer(text, isCorrectValue);
    }
}
