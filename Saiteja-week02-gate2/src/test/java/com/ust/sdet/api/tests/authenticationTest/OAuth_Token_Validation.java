package com.ust.sdet.api.tests.authenticationTest;

import com.ust.sdet.api.API_FrameWork.Factory.SpecFactory;
import com.ust.sdet.api.API_FrameWork.config.ApiConfig;
import com.ust.sdet.api.API_FrameWork.config.AuthConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class OAuth_Token_Validation {
    private static final String BASE_URL = ApiConfig.BASE_URL;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

    }

    @Test
    @DisplayName("Secure Order Access Using OPS Token")
    void secureOrderReadUsingOpsToken() {

        given()
                .spec(SpecFactory.opsRead())

                .when()
                .get(SpecFactory.secureOrderById(5001))

                .then()
                .spec(SpecFactory.ok200())
                .body("id", equalTo(5001));
    }
}
