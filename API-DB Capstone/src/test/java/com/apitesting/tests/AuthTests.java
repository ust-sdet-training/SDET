package com.apitesting.tests;

import com.apitesting.client.AuthClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import com.apitesting.data.testUser;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthTests extends BaseTest {

    @Test
    void shouldLoginSuccessfully() {
        Response response = authClient.login(testUser.EMAIL(), testUser.PASSWORD());

        response.then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("displayName", equalTo("Wendy Warrier"))
                .body("role", equalTo("traveller"));
    }

    @Test
    void shouldReturnCurrentUser() {
        Response response = authClient.getMe(authToken);
        response.then()
                .statusCode(200)
                .body("displayName", equalTo("Wendy Warrier"))
                .body("email", equalTo("wendy@tripstack.test"))
                .body("role", equalTo("traveller"));
    }


    @Test
    void shouldRejectMissingToken() {
        Response response = authClient.getMe("");
        response.then()
                .statusCode(401)
                .body("error", equalTo("unauthorized"));
    }
}