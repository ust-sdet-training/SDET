package org.sdet.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.sdet.API_Framework.config.AppConfig;
import org.sdet.API_Framework.utils.TokenManager;

public abstract class BaseTest {

    @BeforeAll
    static void setupFramework() {

        AppConfig.initialize();

    }

    @BeforeEach
    void setup() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }

    protected void clearToken() {

        TokenManager.clearToken();

    }

}