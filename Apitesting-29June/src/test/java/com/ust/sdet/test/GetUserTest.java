package com.ust.sdet.test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import com.ust.sdet.baseTest.BaseTest;
import com.ust.sdet.modelObject.User;
import com.ust.sdet.resSpec.AllSpec;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GetUserTest extends BaseTest {

    private static final int USER_ID = 1;

    @Test
    @DisplayName("Getting details for userId 1")
    void getPostsForSpecificUser() {

        Response response =
                given()
                .spec(requestSpec)
                .queryParam("userId", USER_ID)
                .when()
                .get("/posts/{userId}", USER_ID)
                .then()
                .spec(AllSpec.successResponse())
                .extract()
                .response();

        User userIdLoop= response.jsonPath().getObject("", User.class);
        assertEquals(USER_ID, userIdLoop.userId(), "User ID should match the requested userId");
        assertFalse(userIdLoop.toString().isEmpty(), "Response should not be empty");
       assertEquals(200, response.getStatusCode(), "Status code should be 200");
        

 
}}
