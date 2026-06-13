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

public class Partner_API_Key_Validation {
    private static final String BASE_URL = ApiConfig.BASE_URL;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

    }

    @Test
    @DisplayName("Partner Order Access Using API Key")
    void partnerOrderAccessUsingApiKey() {

        given()
                .spec(SpecFactory.partnerRead(AuthConfig.API_KEY))

                .when()
                .get(SpecFactory.partnerOrderById(5001))

                .then()
                .spec(SpecFactory.ok200())
                .body("partner", equalTo("UST Partner Channel"))
                .body("order.id", equalTo(5001));
    }
}
