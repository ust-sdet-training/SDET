package com.ust.sdet.api.tests.authenticationTest;

import com.ust.sdet.api.API_FrameWork.Factory.SpecFactory;
import com.ust.sdet.api.API_FrameWork.config.ApiConfig;
import com.ust.sdet.api.API_FrameWork.config.AuthConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Secure_Endpoint_Access_Validation {

    private static final String BASE_URL = ApiConfig.BASE_URL;

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";
    }

    @Test
    @DisplayName("Viewer Can Read Secure Order")
    void viewerCanReadSecureOrder() {

        given()
                .spec(SpecFactory.viewerRead())

                .when()
                .get(SpecFactory.secureOrderById(5001))

                .then()
                .spec(SpecFactory.ok200());
    }
}