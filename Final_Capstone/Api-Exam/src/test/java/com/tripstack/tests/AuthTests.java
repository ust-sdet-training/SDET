package com.tripstack.tests;

import com.tripstack.config.BaseConfig;
import com.tripstack.constants.EndPoints;
import com.tripstack.models.LoginRequest;
import com.tripstack.utils.TokenManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthTests {

    @BeforeAll
    static void setup() {
        BaseConfig.setup();
    }

    @Test
    void loginSuccessfully() {

        LoginRequest request = new LoginRequest("trent@tripstack.test", "Password@123");

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(EndPoints.LOGIN)
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("empId", equalTo("1020"));
    }

    @Test
    void invalidLogin() {

        LoginRequest request = new LoginRequest("trent@tripstack.test", "WrongPassword");

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(EndPoints.LOGIN)
                .then()
                .statusCode(401);
    }

    @Test
    void getCurrentUser() {

        given()
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .when()
                .get(EndPoints.ME)
                .then()
                .statusCode(200)
                .body("empId", equalTo("1020"))
                .body("email", equalTo("trent@tripstack.test"));
    }
}