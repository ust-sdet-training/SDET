package com.tripstack.tests.auth;

import com.tripstack.client.AuthClient;
import com.tripstack.config.ConfigManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@Tag("api")
@Tag("auth")
public class LoginTest {

    @Test
    void loginReturnsTokenAndEmpDetails() {
        AuthClient authClient = new AuthClient();
        Response response = authClient.loginRaw(ConfigManager.EMAIL, ConfigManager.PASSWORD);

        response.then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("empId", equalTo(ConfigManager.EMP_ID))
                .body("role", notNullValue())
                .body("displayName", notNullValue());
    }
}