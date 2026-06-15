package tests;

import base.BaseTest;
import models.request.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigReaderTests extends BaseTest {

    @Test
    @DisplayName("Verify configuration properties are loaded")
    void verifyConfigPropertiesLoaded() {

        assertNotNull(ConfigReader.getBaseUrl());
        assertNotNull(ConfigReader.getDbUrl());
        assertNotNull(ConfigReader.getDbUsername());
        assertNotNull(ConfigReader.getDbPassword());
        assertNotNull(ConfigReader.getOpsClientId());
        assertNotNull(ConfigReader.getOpsClientSecret());
    }

    @Test
    void debugLogin() {

        LoginRequest request =
                new LoginRequest(
                        ConfigReader.getCustomerEmail(),
                        ConfigReader.getCustomerPassword()
                );

        given()
                .contentType("application/json")
                .body(request)

                .when()
                .post("/api/login")

                .then()
                .log().all();
    }
}