package com.tripstack.tests.api.responses;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthResponseExpectations {
    public static final Matcher<Object> LOGIN_SUCCESS = notNullValue();
    public static final Matcher<Object> USER_EMAIL = equalTo("dave@tripstack.test");
}
