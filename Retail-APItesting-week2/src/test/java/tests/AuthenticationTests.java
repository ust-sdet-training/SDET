package tests;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import base.BaseTest;
import models.request.LoginRequest;
import models.response.LoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.RequestSpecs;
import specs.ResponseSpecs;
import utils.ConfigReader;
import utils.TokenManager;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTests extends BaseTest {

    @Test
    @DisplayName("Verify customer login and JWT generation")
    void verifyCustomerLogin() {

        LoginRequest request =
                new LoginRequest(
                        ConfigReader.getCustomerEmail(),
                        ConfigReader.getCustomerPassword()
                );

        LoginResponse response =

                given()
                        .spec(RequestSpecs.basicSpec())
                        .body(request)

                        .when()
                        .post("/api/login")

                        .then()
                        .spec(ResponseSpecs.loginSuccessSpec)
                        .body(
                                matchesJsonSchemaInClasspath(
                                        "schemas/login-schema.json"))
                        .extract()
                        .as(LoginResponse.class);

        assertNotNull(
                response,
                "Response should not be null");

        assertNotNull(
                response.getToken(),
                "Token should not be null");

        assertFalse(
                response.getToken().isBlank(),
                "Token should not be blank");

        assertTrue(
                response.getToken().length() > 20,
                "Token length should be valid");
    }

    @Test
    @DisplayName("Verify OPS OAuth token generation")
    void verifyOpsOAuthTokenGeneration() {

        String token =
                TokenManager.getOpsAccessToken();

        assertNotNull(token);

        assertFalse(token.isBlank());

        assertTrue(
                token.length() > 20,
                "OAuth token should be generated");
    }

    @Test
    @DisplayName("Verify OAuth token response structure")
    void verifyOAuthTokenResponse() {

        given()
                .contentType(
                        "application/x-www-form-urlencoded;charset=UTF-8")
                .formParam(
                        "grant_type",
                        "client_credentials")
                .formParam(
                        "client_id",
                        ConfigReader.getOpsClientId())
                .formParam(
                        "client_secret",
                        ConfigReader.getOpsClientSecret())

                .when()
                .post("/api/oauth/token")

                .then()
                .spec(ResponseSpecs.oauthSuccessSpec)
                .body(
                        matchesJsonSchemaInClasspath(
                                "schemas/oauth-schema.json"))
                .body(
                        "expires_in",
                        greaterThan(0));
    }

    @Test
    @DisplayName("Verify viewer token generation")
    void verifyViewerTokenGeneration() {

        String token =
                TokenManager.getViewerAccessToken();

        assertNotNull(token);

        assertFalse(token.isBlank());

        assertTrue(token.length() > 20);
    }

    @Test
    @DisplayName("Verify invalid login returns unauthorized")
    void verifyInvalidLogin() {

        LoginRequest request =
                new LoginRequest(
                        "invalid@example.com",
                        "wrong-password"
                );

        given()
                .spec(RequestSpecs.basicSpec())
                .body(request)

                .when()
                .post("/api/login")

                .then()
                .spec(ResponseSpecs.unauthorizedSpec);
    }
}