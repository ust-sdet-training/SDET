package com.ust.sdet.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ust.sdet.SpecFactory.BaseSetup.jSONResponseSpec;
import static com.ust.sdet.SpecFactory.BaseSetup.setup;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class InvalidPostIdTest {
    @Test
    @DisplayName("Get the Details of a post that does not exist")
    void getInvalidPostById(){

        Response response =
               given()
               .spec(setup())
                   .pathParam("id",101)
                .when()
                .get("/posts/{id}")
                .then()
               .spec(jSONResponseSpec(404))
               .extract().response();

    }
}
