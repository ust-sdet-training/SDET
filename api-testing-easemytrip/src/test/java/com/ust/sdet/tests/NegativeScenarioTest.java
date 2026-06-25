package com.ust.sdet.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ust.sdet.specifications.CommonSpec.commonReqSpec;
import static com.ust.sdet.specifications.CommonSpec.commonResSpec;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;

public class NegativeScenarioTest {
    @Test
    @DisplayName("N1: GET /post status code 404")
    void getPostsTest(){
        given()
                .spec(commonReqSpec())
                .when()
                .get("/posts/{id}", 1000)
                .then()
                .statusCode(404);
    }
}
