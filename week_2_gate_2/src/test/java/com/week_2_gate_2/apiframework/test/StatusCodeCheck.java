package com.week_2_gate_2.apiframework.test;
import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.week_2_gate_2.apiframework.support.ApiSupport;
import static com.week_2_gate_2.fixtures.TestEnvironmentVariables.*;
import static com.week_2_gate_2.apiframework.support.ApiSupport.*;

import com.week_2_gate_2.dbframework.support.DBFunctions;
import com.week_2_gate_2.dbframework.support.DbSupport;

import groovyjarjarantlr4.v4.parse.ANTLRParser.prequelConstruct_return;

import static com.week_2_gate_2.fixtures.order.*;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.week_2_gate_2.dbframework.config.DBconfig;
import com.week_2_gate_2.dbframework.model.orderRow;

import io.restassured.specification.RequestSpecification;

public class StatusCodeCheck {
    
    private static String authToken;
    private static String viewToken;
    

    @BeforeAll
    static void setup(){
        authToken = ApiSupport.Fetch(TOKEN_URL_CLIENT_ID(), TOKEN_URL_CLIENT_SECRET());

    }


     @Test
    @DisplayName("checking Unkown element is there or not")
    void cartCheck(){
        given()
            .spec(authed(authToken))
            .pathParam("id", 99999)
        .when()
            .get("/secure/orders/{id}")
        .then()
            .statusCode(404)
            .body("message", equalTo("Order not found"));
    }

     @Test
    @DisplayName("checking Unkown element is there or not")
    void notyloginpost_401(){
        given()
            .spec(notAuthed)
            .pathParam("id", 99999)
        .when()
            .get("/secure/orders/{id}")
        .then()
            .statusCode(401)
            .body("message", equalTo("Authentication required"));
    }

    @Test
    @DisplayName("Calling Allocate after Shipped for 409")
    void shipedProductAllocate_409(){
      given().spec(authed(authToken))
              .when().post("/secure/orders/{id}/allocate",5026)
              .then().spec(res409);
    }

    




}