package com.ust.sdet.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ust.sdet.SpecFactory.BaseSetup.jSONResponseSpec;
import static com.ust.sdet.SpecFactory.BaseSetup.setup;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class DeletePostByIdTest {
    @Test
    @DisplayName("To Delete a post by id")
    void deletePostById(){

        int id =4;

        Response response =
               given()
                       .spec(setup())
                   .pathParam("id",id)
                .when()
                .delete("/posts/{id}")
                .then()
               .spec(jSONResponseSpec(200))
               .extract().response();

        System.out.println(response.asPrettyString());





    }

}
