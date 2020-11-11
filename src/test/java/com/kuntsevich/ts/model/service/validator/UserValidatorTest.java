package com.kuntsevich.ts.model.service.validator;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UserValidatorTest {
    private final UserValidator userValidator = new UserValidator();

    @Test
    public void testIsIdValidPositive() {
        assertTrue(userValidator.isIdValid("1"));
    }

    @Test
    public void testIsUsernameValidPositive() {
        assertTrue(userValidator.isUsernameValid("user"));
    }

    @Test
    public void testIsNameValidPositive() {
        assertTrue(userValidator.isNameValid("Я"));
    }

    @Test
    public void testIsSurnameValidPositive() {
        assertTrue(userValidator.isSurnameValid("Я"));
    }

    @Test
    public void testIsEmailValidPositive() {
        assertTrue(userValidator.isEmailValid("user@user.user"));
    }

    @Test
    public void testIsPasswordValidPositive() {
        assertTrue(userValidator.isPasswordValid("u1s2e3r4"));
    }

    @Test
    public void testIsRoleValidPositive() {
        assertTrue(userValidator.isRoleValid("Студент"));
    }

    @Test
    public void testIsIdValidNegative() {
        assertFalse(userValidator.isIdValid("-1"));
    }

    @Test
    public void testIsUsernameValidNegative() {
        assertFalse(userValidator.isUsernameValid(""));
    }

    @Test
    public void testIsNameValidNegative() {
        assertFalse(userValidator.isNameValid(""));
    }

    @Test
    public void testIsSurnameValidNegative() {
        assertFalse(userValidator.isSurnameValid(""));
    }

    @Test
    public void testIsEmailValidNegative() {
        assertFalse(userValidator.isEmailValid("u@u.u"));
    }

    @Test
    public void testIsPasswordValidNegative() {
        assertFalse(userValidator.isPasswordValid("123"));
    }

    @Test
    public void testIsRoleValidNegative() {
        assertFalse(userValidator.isRoleValid("123"));
    }
}