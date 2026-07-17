package com.tripstack.base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    @BeforeAll
    static void setup() {

        RestAssured.requestSpecification =
                RequestSpecificationBuilder.requestSpecification();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}