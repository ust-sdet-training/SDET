package com.apitesting.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.apitesting.models.PostModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static io.restassured.RestAssured.given;
import static com.apitesting.support.RequestSpec.requestSpec;
import static com.apitesting.support.ResponseSpec.getResponse;


public class ApiTest {
     @Test
    @DisplayName("Get a Post of id 1 using path param")
    void getAPost(){
       PostModel post =given()
            .spec(requestSpec)
            .pathParam("id", 1)
        .when()
            .get("/posts/{id}")
        .then()
            .spec(getResponse)
            .body("id", equalTo(1))
            .body("userId", notNullValue())
            .body("title", notNullValue())
            .body("body", notNullValue())
            .extract().as(PostModel.class);
        
        assertEquals(post.id(),1);
        assertNotNull(post.userId());
        assertNotNull(post.title());
        assertFalse(post.title().isBlank());
        assertNotNull(post.body()); 
         assertFalse(post.body().isBlank());

    }
}
