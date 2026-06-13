package com.week_2_gate_2.apiframework.test;
import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.SQLException;
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
    private static String viwerToken;
    private static DbSupport database;
    

    @BeforeAll
    static void setup(){
        authToken = ApiSupport.Fetch(TOKEN_URL_CLIENT_ID(), TOKEN_URL_CLIENT_SECRET());
        viwerToken = ApiSupport.Fetch(VIEWER_CLIENT_ID(), VIEWER_CLIENT_SECRET());
        database = new DbSupport(DBconfig.fromEnvironment());
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
    @DisplayName("checking cart without login for 401 error")
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
    void shipedProductAllocate_409() throws SQLException{

        Response response = given()
                                .spec(authed(authToken))
                                .body(orderRequest)
                            .when()
                                .post("/secure/orders")
                            .then()
                                .spec(postJson)
                                .body("id", notNullValue())
                                .body("orderId", notNullValue())
                                .body(matchesJsonSchemaInClasspath("schemas/json/order.schema.json"))
                                .extract()
                                .response();
        
        long id =((Number) response.path("orderId")).longValue();
        orderRow row =  database.findOrder(id);

      given().spec(authed(authToken))
              .when().post("/secure/orders/{id}/ship",id)
              .then().spec(res409)
              .body("message", equalTo("Cannot move order from CREATED to SHIPPED"));
    }

    @Test
    @DisplayName("Not logined person checking the unkown product.Verifiying with 401 error")
    void wrongRole_is403(){

        

        given()
            .spec(authed(viwerToken))
             .body(Map.of("items",List.of(101,107),"currency","INR"))
        .when()
            .post("/secure/orders")
        .then()
            .statusCode(403)
            .body("message",equalTo("OPS role required"));
    }



    




}