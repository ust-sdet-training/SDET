package com.ust.sdet.base;

import com.ust.sdet.config.TestConfig;
import io.restassured.RestAssured;

import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = TestConfig.BASE_URL;
    }
}