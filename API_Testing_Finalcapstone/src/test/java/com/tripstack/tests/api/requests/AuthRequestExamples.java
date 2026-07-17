package com.tripstack.tests.api.requests;

public class AuthRequestExamples {
    public static final String VALID_LOGIN_BODY = "{\"email\":\"dave@tripstack.test\",\"password\":\"Password@123\"}";
    public static final String INVALID_LOGIN_BODY = "{\"email\":\"unknown@tripstack.test\",\"password\":\"wrong\"}";
}
