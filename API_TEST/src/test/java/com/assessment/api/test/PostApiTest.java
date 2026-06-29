package com.assessment.api.test;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.assessment.api.util.SpecFactory.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class PostApiTest {

    @Test
    @DisplayName("Retrieve Post and Validate Details")
    void validatePostDetails() {

        JsonPath json =

                given()
                        .spec(reqSpec())
                        .when()
                        .get(postURL())
                        .then()
                        .statusCode(200)
                        .extract()
                        .jsonPath();

        assertEquals(1, json.getInt("id"));
        assertNotNull(json.get("userId"));
        assertFalse(json.getString("title").isEmpty());
        assertFalse(json.getString("body").isEmpty());

        json.prettyPrint();
    }
}
