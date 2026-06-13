package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.ConfigReader;
import models.request.LoginRequest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigReaderTests{

    @Test
    @DisplayName("Verify configuration properties are loaded")
    void verifyConfigPropertiesLoaded() {

        assertNotNull(
                ConfigReader.getBaseUrl());

        assertNotNull(
                ConfigReader.getDbUrl());

        assertNotNull(
                ConfigReader.getDbUsername());

        assertNotNull(
                ConfigReader.getDbPassword());

        assertNotNull(
                ConfigReader.getOpsClientId());

        assertNotNull(
                ConfigReader.getOpsClientSecret());
    }
    @Test
    void verifyBaseUrlReachable() {

        given()

                .when()
                .get("/")

                .then()
                .log().all();
    }
    @Test
    void debugLogin() {

        LoginRequest request =
                new LoginRequest(
                        ConfigReader.getCustomerEmail(),
                        ConfigReader.getCustomerPassword()
                );

        System.out.println("Email = " + request.getEmail());
        System.out.println("Password = " + request.getPassword());

        given()
                .contentType("application/json")
                .body(request)

                .when()
                .post("/api/login")

                .then()
                .log().all();
    }
}