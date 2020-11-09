package com.kuntsevich.ts.model.service.creator;

import com.kuntsevich.ts.entity.Credential;
import com.kuntsevich.ts.model.service.exception.CreatorException;
import com.kuntsevich.ts.model.service.validator.UserValidator;

public class CredentialCreator {
    public static Credential createCredential(String userId, String userHash) throws CreatorException {
        if (userId == null || userHash == null) {
            throw new CreatorException("Parameters are null");
        }
        UserValidator userValidator = new UserValidator();
        if (!userValidator.isIdValid(userId)) {
            throw new CreatorException("Parameters are incorrect");
        }
        long id = Long.parseLong(userId);
        Credential credential = new Credential(id, userHash);
        return credential;
    }
}
