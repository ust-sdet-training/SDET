package com.ust.sdet.accountWorks.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.ust.sdet.accountWorks.Specbuilder.SpecFactory.getReqSpecToPosts1;
import static com.ust.sdet.accountWorks.contracts.ContractAsserts.assertOkJsonContract;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class ApiTests {
//    As a QA Engineer, I want to retrieve a post and validate its details to ensure the API returns the expected data.

    @Test
    @DisplayName("QUESTION: SEND GET REQUEST TO /posts/1")
    void getRequestToPosts1() {
        Response response =
                given()
                    .spec(getReqSpecToPosts1("/posts/1"))
                    .when()
                    .get()
                    .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

//        Validate statusCode 200
        assertEquals(200, response.statusCode());
//        Validate id = 1
        assertEquals(1, Integer.parseInt(response.jsonPath().getString("userId")));
//        Validate userId is present
        assertFalse(response.jsonPath().getString("id").isBlank());
//       Validate title is not empty
        assertFalse(response.jsonPath().getString("title").isEmpty());
//       Validate body is not empty
        assertFalse(response.jsonPath().getString("body").isEmpty());

//        Extra Validation Schema Validation
        assertOkJsonContract(response, "schemas/json/response.schema.json");
    }


}
