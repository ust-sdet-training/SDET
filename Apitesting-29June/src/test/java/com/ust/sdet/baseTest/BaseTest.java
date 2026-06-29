package com.ust.sdet.baseTest;

import com.ust.sdet.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    protected RequestSpecification requestSpec;

    @BeforeEach
    public void setup() {

        RestAssured.baseURI = ConfigManager.BASE_URL;

        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .build();
    }
}
