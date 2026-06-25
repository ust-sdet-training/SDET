package com.apitest.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static io.restassured.RestAssured.given;
import static com.apitest.support.RequestSpec.requestSpec;
import static com.apitest.models.PostData.post;

import static com.apitest.support.ResponceSpec.postResponse;
import static com.apitest.support.ResponceSpec.getResponse;

import static com.apitest.support.ResponceSpec.deleteResponse;

public class APITests {
    
    @Test
    @DisplayName("Add a Post and validate the schema")
    void addAPost(){
       Response result =  given()
            .spec(requestSpec)
            .body(post)
        .when()
            .post("/posts/")
        .then()
            .spec(postResponse)
            .extract()
            .response();
        assertNotNull(result.path("id"));
        assertEquals(result.path("title"), post.get("title"));
        assertEquals(result.path("body"), post.get("body"));
    }

    @Test
    @DisplayName("Get a Post of id 1 using path param")
    void getAPost(){
       given()
            .spec(requestSpec)
            .pathParam("id", 1)
        .when()
            .get("/posts/{id}")
        .then()
            .body("id", equalTo(1))
            .body("userId", notNullValue())
            .spec(getResponse);
        
    }

    @Test
    @DisplayName("Update title and content of the post id =1")
    void putAPost(){
       given()
            .spec(requestSpec)
            .pathParam("id", 1)
            .body(post)
        .when()
            .put("/posts/{id}")
        .then()
            .spec(getResponse)
            .body("id", equalTo(1))
            .body("title", equalTo(post.get("title")))
            .body("body", equalTo(post.get("body")));
    }

    @Test
    @DisplayName("Delete the post with id =1")
    void deleteAPost(){
       given()
            .spec(requestSpec)
            .pathParam("id", 1)
            .body(post)
        .when()
            .delete("/posts/{id}")
        .then()
            .spec(deleteResponse);
    }

}

