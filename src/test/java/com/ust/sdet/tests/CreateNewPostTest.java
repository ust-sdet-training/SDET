package com.ust.sdet.tests;

import com.ust.sdet.Functions.Functions;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.ust.sdet.SpecFactory.BaseSetup.jSONResponseSpec;
import static com.ust.sdet.SpecFactory.BaseSetup.setup;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class CreateNewPostTest {
    @Test
    @DisplayName("Create a new post")
    void createANewPost(){

        int id = 101;


       Map newpost =  Functions.createABody(
               id,
               id,
               "Basic Test",
               "creating a new post.");



        Response response =
               given()
               .spec(setup())
                       .body(newpost)
                .when()
                .post("/posts")
                .then()
               .spec(jSONResponseSpec(201))
               .body(matchesJsonSchemaInClasspath("schemas/json/post.schema.json"))
               .body("id",equalTo(id))
               .extract().response();

        System.out.println(response.asPrettyString());





    }

}
