package com.ust.sdet.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.ust.sdet.specifications.CommonSpec.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PositiveScenarioTest {

    @Test
    @DisplayName("M1: GET /post")
    void getPostsTest(){
        given()
                .spec(commonReqSpec())
        .when()
                .get("/posts")
        .then()
                .spec(commonResSpec())
                .body(matchesJsonSchemaInClasspath("schemas/post.schema.json"))
                .body("userId", notNullValue())
                .body("id", notNullValue())
                .body("title", notNullValue())
                .body("items.size()", equalTo(100));
    }

    @Test
    @DisplayName("M2: POST /post")
    void postPostsTest(){
        Map data = Map.of(
                "title", "foo",
                "body", "bar",
                "userId", 1
        );
        given()
                .spec(commonReqSpec())
                .body(data)
        .when()
                .post("/posts")
        .then()
                .spec(postResSpec())
                .body(matchesJsonSchemaInClasspath("schemas/post-put.schema.json"))
                .body("id", notNullValue())
                .body("userId", equalTo(data.get("userId")))
                .body("title", equalTo(data.get("title")))
                .body("body", equalTo(data.get("body")));
    }

    @Test
    @DisplayName("M3: PUT /post")
    void putPostsTest(){
        Map data = Map.of(
                "title", "foo",
                "body", "bar",
                "userId", 1
        );
        given()
                .spec(commonReqSpec())
                .body(data)
                .when()
                .put("/posts/{id}", 1)
                .then()
                .spec(commonResSpec())
                .body(matchesJsonSchemaInClasspath("schemas/post-put.schema.json"))
                .body("id", notNullValue())
                .body("userId", equalTo(data.get("userId")))
                .body("title", equalTo(data.get("title")))
                .body("body", equalTo(data.get("body")));
    }
    @Test
    @DisplayName("M4: DELETE /post")
    void deletePostsTest(){
        given()
                .spec(commonReqSpec())
                .when()
                .delete("/posts/{id}", 1)
                .then()
                .spec(commonResSpec());
    }

}
