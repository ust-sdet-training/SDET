package com.ust.sdet.api.tests;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static com.ust.sdet.api.support.SpecFactory.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class apiTest {

    @Test
    @DisplayName("M1: As a QA Engineer, I want to create a new post and verify the response.")
    void testGet() {
        var requestBody = Map.of(
                "title", "Automation Testing",
                "body", "Rest Assured Assignment",
                "userId", 1);

         var response = given()
                 .spec(jsonRequest)
                 .body(requestBody)
                 .when()
                 .post("/posts")
                 .then()
                 .spec(jsonCreateResponse)
                 .extract()
                 .response();
         JsonPath json=response.jsonPath();

       assertThat(response.statusCode(),equalTo(201));
       assertThat(json.getString("body"),equalTo("Rest Assured Assignment"));
        assertThat(json.getInt("userId"),equalTo(1));
        assertThat(json.getString("title"),equalTo("Automation Testing"));
        assertThat(json.getInt("id"),equalTo(101));
    }
}
