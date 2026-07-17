package org.sdet.tests.auth;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.sdet.base.BaseTest;
import org.sdet.builders.LoginBuilder;
import org.sdet.model.request.LoginRequest

import static org.hamcrest.Matchers.notNullValue;

public class LoginTests extends BaseTest {

    @Test
    public void verifyValidLogin() {

        LoginRequest request = LoginBuilder.defaultLogin();

        Response response = authClient.login(request);

        response.then()
                .statusCode(200)
                .body("token", notNullValue());

    }
}