package com.ust.sdet.tests;

import com.ust.sdet.base.BaseApiTest;
import com.ust.sdet.config.TestConfig;
import com.ust.sdet.models.AuthModels;
import com.ust.sdet.specs.RequestSpecs;
import com.ust.sdet.specs.ResponseSpecs;
import io.restassured.response.Response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthTest
        extends BaseApiTest {

    @Test
    @DisplayName("Login should return token")
    void login_shouldReturn200() {

        AuthModels.LoginRequest request = new AuthModels.LoginRequest(
                        TestConfig.EMAIL,
                        TestConfig.PASSWORD
                );
        Response response =
                given()
                        .spec(RequestSpecs.jsonRequestSpec())
                        .body(request)
                        .when()
                        .post("/api/auth/login")
                        .then()
                        .spec(ResponseSpecs.ok200Spec())
                        .body("token", notNullValue(), "user.email",
                                equalTo(TestConfig.EMAIL),
                                "user.role",
                                equalTo("customer")
                        )
                        .body(matchesJsonSchemaInClasspath(
                                        "schemas/json/login-schema.json"
                                )
                        )
                        .extract()
                        .response();

        assertStatus(response, 200);
        assertResponseBody(response);
        assertNotNull(response.path("token")
        );
    }

    @Test
    @DisplayName("Login should fail for wrong password")
    void login_invalidPassword_shouldReturn401() {

        AuthModels.LoginRequest request =
                new AuthModels.LoginRequest(
                        TestConfig.EMAIL,
                        "wrong"
                );

        Response response =
                given()
                        .spec(RequestSpecs.jsonRequestSpec())
                        .body(request)
                        .when()
                        .post("/api/auth/login")
                        .then()
                        .spec(ResponseSpecs.unauthorized401Spec())
                        .body("message", equalTo("Invalid credentials"))
                        .body(matchesJsonSchemaInClasspath(
                                "schemas/json/error-schema.json"
                                )
                        )
                        .extract()
                        .response();

        assertStatus(response, 401);
    }

    @Test
    @DisplayName("Login should fail for invalid email")
    void login_invalidEmail_shouldReturn401() {

        AuthModels.LoginRequest request = new AuthModels.LoginRequest(
                        "invalid@example.com",
                        TestConfig.PASSWORD
                );

        given()
                .spec(RequestSpecs.jsonRequestSpec())
                .body(request)
                .when()
                .post("/api/auth/login")
                .then()
                .spec(ResponseSpecs.unauthorized401Spec())
                .body("message", equalTo("Invalid credentials"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/json/error-schema.json"
                        )
                );

    }

    @Test
    @DisplayName("Locked account should return 423")
    void login_lockedUser_shouldReturn423() {

        AuthModels.LoginRequest request = new AuthModels.LoginRequest(
                "locked@example.com",
                "Password@123"
                );

        given()
                .spec(RequestSpecs.jsonRequestSpec())
                .body(request)
                .when()
                .post("/api/auth/login")
                .then()
                .spec(ResponseSpecs.locked423Spec())
                .body("message", equalTo("Account is locked"))
                .body(matchesJsonSchemaInClasspath(
                        "schemas/json/error-schema.json"
                        )
                );
    }

    @Test
    @DisplayName("OAuth token should return 200")

    void oauth_shouldReturn200() {
        given()
                .spec(RequestSpecs.formRequestSpec())
                .spec(RequestSpecs.oauthformparams())
                .when()
                .post("/api/oauth/token")
                .then()
                .spec(ResponseSpecs.ok200Spec())
                .body("access_token", notNullValue());
    }

    @Test
    @DisplayName("OAuth invalid secret should return 401")

    void oauth_invalidSecret_shouldReturn401() {
        given()
                .spec(RequestSpecs.formparams())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .accept("application/json")
                .when()
                .post("/api/oauth/token")
                .then()
                .spec(ResponseSpecs.unauthorized401Spec())
                .body("error", equalTo("invalid_client"));
    }
}