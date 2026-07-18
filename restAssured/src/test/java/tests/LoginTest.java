package tests;

import api.clients.AuthClient;
import api.clients.BookingClient;
import api.specs.ResponseSpecs;
import builders.LoginRequestBuilder;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import models.BookingPnrRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import security.SecurityConstants;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginTest {

    private final AuthClient authClient = new AuthClient();

    @Test
    void shouldLoginSuccessfully() {

        Response response = authClient.login(
                new LoginRequestBuilder()
                        .withDefaultUser()
                        .build());

        response.then()
                .spec(ResponseSpecs.success())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/login-schema.json"))
                .body("token", notNullValue());

    }

}
