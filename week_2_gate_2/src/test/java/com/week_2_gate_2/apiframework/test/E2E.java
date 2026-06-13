package com.week_2_gate_2.apiframework.test;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
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
public class E2E {
    
    private static String authToken;
    private static DbSupport database;
    private static String apiKey;
    
    private static final List<Long> createdOrderIds = new ArrayList<>();

    @BeforeAll
    static void setup(){
        authToken = ApiSupport.Fetch(TOKEN_URL_CLIENT_ID(), TOKEN_URL_CLIENT_SECRET());

        database = new DbSupport(DBconfig.fromEnvironment());
        apiKey = API_KEY();

    }

    @AfterEach
    void cleanup() throws Exception{
      for(long orderId:createdOrderIds){
          database.deleteOrder(orderId);
      }
      createdOrderIds.clear();
    }

    @Test
    @DisplayName("Create order -> allocate order -> order shipped And Api Layer too")
    void createOrder() throws Exception{
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
        createdOrderIds.add(id);
        orderRow row =  database.findOrder(id);

        DBFunctions.check(row,"CREATED","orderNumber","svc-retail-ops",response);

       Response allocate = given()
                                .spec(authed(authToken))
                            .when()
                                .post("/secure/orders/{id}/allocate",id)
                            .then()
                                .spec(okJson)
                                .body("id", notNullValue())
                                .body("orderId", notNullValue())
                                .body(matchesJsonSchemaInClasspath("schemas/json/order.schema.json"))
                                .extract()
                                .response();

        DBFunctions.check( database.findOrder(id), "ALLOCATED", "orderNumber", "svc-retail-ops", allocate);


        Response shipped  = given()
                                .spec(authed(authToken))
                            .when()
                                .post("/secure/orders/{id}/ship",id)
                            .then()
                                .spec(okJson)
                                .body("id", notNullValue())
                                .body("orderId", notNullValue())
                                .body(matchesJsonSchemaInClasspath("schemas/json/order.schema.json"))
                                .extract()
                                .response();

        DBFunctions.check( database.findOrder(id), "SHIPPED", "orderNumber", "svc-retail-ops", shipped);
          
        Response apiResponse=  given()
                                    .spec(authed(authToken))
                                    .when()
                                    .get("/secure/orders/{id}",id)
                                    .then()
                                    .spec(okJson)
                                    .body(matchesJsonSchemaInClasspath("schemas/json/order.schema.json")).extract().response();
        DBFunctions.check( database.findOrder(id), "SHIPPED", "orderNumber", "svc-retail-ops", apiResponse);
    }




}
