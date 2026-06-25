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

public class UpdatePostUsingPatchTest {
    @Test
    @DisplayName("Update a post Using Patch")
    void updateAPostUsingPut(){
        int id = 1;
        int newuserid =100;

        Functions.createAGetRequest(id);

        Map newpost =  Functions.createAPatchBody(
               newuserid,
               id
               );



        Response response =
               given()
               .spec(setup())
                       .body(newpost)
                       .pathParam("id",id)
                .when()
                .patch("/posts/{id}")
                .then()
               .spec(jSONResponseSpec(200))
                .body(matchesJsonSchemaInClasspath("schemas/json/post.schema.json"))
               .body("id",equalTo(id))
               .body("userId",equalTo(newuserid))
               .extract().response();

        System.out.println(response.asPrettyString());





    }

}
