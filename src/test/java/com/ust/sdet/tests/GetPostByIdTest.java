package com.ust.sdet.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.ust.sdet.SpecFactory.BaseSetup.*;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class GetPostByIdTest {
    @Test
    @DisplayName("Get the Details of a post by id")
    void getPostById(){

        int id =4;

        Response response =
               given()
                       .spec(setup())
                   .pathParam("id",id)
                .when()
                .get("/posts/{id}")
                .then()
               .spec(jSONResponseSpec(200))
               .body(matchesJsonSchemaInClasspath("schemas/json/post.schema.json"))
               .body("id",equalTo(id))
               .extract().response();

        System.out.println(response.asPrettyString());





    }

}
