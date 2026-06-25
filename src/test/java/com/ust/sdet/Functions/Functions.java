package com.ust.sdet.Functions;

import io.restassured.response.Response;

import java.util.Map;

import static com.ust.sdet.SpecFactory.BaseSetup.jSONResponseSpec;
import static com.ust.sdet.SpecFactory.BaseSetup.setup;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Functions {
    public static Map createABody(int userId, int id, String title, String body){

        return Map.of("userId",userId,
                "id",id,
                "title",title,
                "body",body);

    }

    public static Map createAPatchBody(int userId, int id){

        return Map.of("userId",userId,
                "id",id);

    }

    public static int createAPostRequest(Map payload){
        return given()
                .spec(setup())
                .body(payload)
                .when()
                .post("/posts/")
                .then()
                .spec(jSONResponseSpec(201))
                .body(matchesJsonSchemaInClasspath("schemas/json/post.schema.json"))
                .extract().path("id");
    }
    public static void createAGetRequest(int id){
        Response response = given()
                .spec(setup())
                .pathParam("id",id)
                .when()
                .get("/posts/{id}")
                .then()
                .spec(jSONResponseSpec(200))
                .body(matchesJsonSchemaInClasspath("schemas/json/post.schema.json"))
                .extract().response();

        System.out.println(response.asPrettyString());
    }
}
