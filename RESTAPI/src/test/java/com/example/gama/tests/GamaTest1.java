package com.example.gama.tests;

import com.example.gama.Specfactory.RequestSpec;
import com.example.gama.Specfactory.ResponseSpec;
import com.example.gama.config.TestEnvironment;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GamaTest1 {

    private final String POST = TestEnvironment.required("POST_USER_DATA");

    private final String PUT = TestEnvironment.required("PUT_USER_DATA");


    private static RequestSpecification get;
    private static RequestSpecification getwithparam;

    @BeforeAll
    static void setup() {

        get = RequestSpec.request();
        getwithparam = RequestSpec.requestwithparam();
    }

    @Test
    @DisplayName("All HTTP methods")
    void apiFlow() {

        given().spec(get)
                .contentType(ContentType.JSON)
                .when()
                .get("/1")
                .then()
                .spec(ResponseSpec.validate200Response());

        given().spec(get)
                .contentType(ContentType.JSON)
                .body(POST)
                .when()
                .post("")
                .then()
                .spec(ResponseSpec.validate201Response());

        given().spec(get)
                .contentType(ContentType.JSON)
                .body(PUT)
                .when()
                .put("/12")
                .then()
                .spec(ResponseSpec.validate200Response());


        given().spec(get)
                .contentType(ContentType.JSON)
                .when()
                .delete("/1")
                .then()
                .statusCode(200);


    }

    @Test
    @DisplayName(" Get Method Verification")
    void getFlow() {

        given().spec(get)
                .contentType(ContentType.JSON)
                .when()
                .get("")
                .then()
                .spec(ResponseSpec.validate200Responseofarray());


        given().spec(get)
                .contentType(ContentType.JSON)
                .when()
                .get("/1/comments")
                .then()
                .spec(ResponseSpec.validate200Responseofarray());

    }

    @Test
    @DisplayName(" Get Method with param Verification")
    void paramFlow() {

        given().spec(getwithparam)
                .contentType(ContentType.JSON)
                .when()
                .get("")
                .then()
                .spec(ResponseSpec.validate200Responseofarray());




    }
}