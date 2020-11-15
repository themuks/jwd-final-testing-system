package com.kuntsevich.ts.model.service.creator;

import com.kuntsevich.ts.entity.Credential;
import com.kuntsevich.ts.model.service.exception.CreatorException;
import com.kuntsevich.ts.validator.EntityValidator;

public class CredentialCreator {
    public static Credential createCredential(String userId, String userHash, String emailHash) throws CreatorException {
        if (userId == null
                || userHash == null
                || emailHash == null) {
            throw new CreatorException("Parameters are null");
        }
        if (!EntityValidator.isIdValid(userId)) {
            throw new CreatorException("Parameters are incorrect");
        }
        try {
            long id = Long.parseLong(userId);
            return new Credential(id, userHash, emailHash);
        } catch (NumberFormatException e) {
            throw new CreatorException("Error while parsing integer", e);
        }
    }

    public static Credential createCredential(String userHash, String emailHash) throws CreatorException {
        if (userHash == null || emailHash == null) {
            throw new CreatorException("Parameters are null");
        }
        return new Credential(userHash, emailHash);
    }
}
