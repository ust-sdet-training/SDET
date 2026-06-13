package com.ust.sdet.api.tests;

import com.ust.sdet.api.config.DatabaseConfig;
import com.ust.sdet.api.model.OrderRow;
import com.ust.sdet.api.support.DbSupport;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ust.sdet.api.specificationfactory.SpecFactory.*;
import static com.ust.sdet.api.specificationfactory.SpecFactory.commonJsonFetchResponse;
import static com.ust.sdet.api.specificationfactory.SpecFactory.commonJsonRequest;
import static com.ust.sdet.api.specificationfactory.SpecFactory.getToken;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCycleWithDbValidationTest
{

    private static final List<Long> createOrderIds = new ArrayList<>();
    private static  String token;
    private static DbSupport database;

    @BeforeAll
    static void setup() {

        database = new DbSupport(DatabaseConfig.fromEnvironmentCredential());
        token = getToken(System.getenv("OAUTH_CLIENT_ID"), System.getenv("OAUTH_CLIENT_SECRET"));
    }

    @AfterEach
    void cleanCreateRows() throws Exception {
        for (long orderId : createOrderIds) {
            database.deleteOrder(orderId);
        }
        createOrderIds.clear();
    }

    @Test
    @DisplayName("Local M1:MySql is reachable through JDBC or not it true give reachable")
    void local_MySqlIs_Reachable() throws Exception {
        assertTrue(database.isReachable());
    }

    //created order cycle with create , allocated,shipped
    @Test
    @DisplayName((" M3: order persists  with cycle create ,allocated, shipped with happy path "))
    void createOrder_isPersisted_cycle() throws Exception {
        Map<String, Object> newOrder = Map.of(
                "items", List.of(106, 112),
                "currency", "INR"
        );
        Response response = given()
                .spec(authedOrderCreatedRequest)
                .auth().oauth2(token)
                .body(newOrder)
                .when().post()
                .then()
                .spec(commonJsonCreateResponse)
                .body(matchesJsonSchemaInClasspath("schemas/json/orderAuth.schema.json"))
                .extract().response();
       long  id = response.jsonPath().getLong("orderId");
        createOrderIds.add(id);

        OrderRow row = database.findOrder(id);

        assertNotNull(row, "order must be persisted");
        assertEquals("CREATED", row.status());
        assertEquals("svc-retail-ops", row.userId());
        assertEquals(0, row.total().compareTo(response.jsonPath().getObject("total", BigDecimal.class)));


        //allocated
        var response1 =  given()
                .spec(commonJsonRequest)
                .auth().oauth2(token)
                .when()
                .post("/secure/orders/{id}/allocate", id)
                .then()
                .spec(commonJsonFetchResponse)
                .body("id", equalTo((int) id))
                .body("status", equalTo("ALLOCATED"))
                .body(matchesJsonSchemaInClasspath("schemas/json/orderAuth.schema.json"))
                .extract().response();

        JsonPath json = response1.jsonPath();
        assertEquals(id,json.getInt("id"));
        assertEquals("ALLOCATED",json.getString("status"));
        assertEquals("API Fulfilment Queue", json.getString("address"));
        assertEquals("Standard", json.getString("deliverySlot"));
        assertEquals("svc-retail-ops", json.getString("userId"));


        //shipped

        var shipped =  given()
                .spec(commonJsonRequest)
                .auth().oauth2(token)
                .when()
                .post("/secure/orders/{id}/ship", id)
                .then()
                .spec(commonJsonFetchResponse)
                .body("id", equalTo((int) id))
                .body("status", equalTo("SHIPPED"))
                .body(matchesJsonSchemaInClasspath("schemas/json/orderAuth.schema.json"))
                .extract().response();

        JsonPath shippedJson = shipped.jsonPath();

        assertEquals(id,shippedJson.getInt("id"));
        assertEquals("SHIPPED",shippedJson.getString("status"));
        assertEquals("API Fulfilment Queue", shippedJson.getString("address"));
        assertEquals("Standard", shippedJson.getString("deliverySlot"));
        assertEquals("svc-retail-ops", shippedJson.getString("userId"));

    }

    //created order cycle with create , allocated,shipped
    @Test
    @DisplayName((" M3: order persists  with cycle create ,allocated, shipped with unhappy path "))
    void createOrder_isPersisted_cycle_negative() throws Exception {
        Map<String, Object> newOrder = Map.of(
                "items", List.of(108, 110),
                "currency", "INR"
        );
        Response response = given()
                .spec(authedOrderCreatedRequest)
                .auth().oauth2(token)
                .body(newOrder)
                .when().post()
                .then()
                .spec(commonJsonCreateResponse)
                .body(matchesJsonSchemaInClasspath("schemas/json/orderAuth.schema.json"))
                .extract().response();
       long id = response.jsonPath().getLong("orderId");
        createOrderIds.add(id);

        OrderRow row = database.findOrder(id);

        assertNotNull(row, "order must be persisted");
        assertEquals("CREATED", row.status());
        assertEquals("svc-retail-ops", row.userId());
        assertEquals(0, row.total().compareTo(response.jsonPath().getObject("total", BigDecimal.class)));


        //allocated
        var response1 =  given()
                .spec(commonJsonRequest)
                .auth().oauth2(token)
                .when()
                .post("/secure/orders/{id}/allocate", id)
                .then()
                .spec(commonJsonFetchResponse)
                .body("id", equalTo((int) id))
                .body("status", equalTo("ALLOCATED"))
                .body(matchesJsonSchemaInClasspath("schemas/json/orderAuth.schema.json"))
                .extract().response();

        JsonPath json = response1.jsonPath();
        assertEquals(id,json.getInt("id"));
        assertEquals("ALLOCATED",json.getString("status"));
        assertEquals("API Fulfilment Queue", json.getString("address"));
        assertEquals("Standard", json.getString("deliverySlot"));
        assertEquals("svc-retail-ops", json.getString("userId"));


        //shipped

        var shipped =  given()
                .spec(commonJsonRequest)
                .auth().oauth2(token)
                .when()
                .post("/secure/orders/{id}/ship", id)
                .then()
                .spec(commonJsonFetchResponse)
                .body("id", equalTo((int) id))
                .body("status", equalTo("SHIPPED"))
                .body(matchesJsonSchemaInClasspath("schemas/json/orderAuth.schema.json"))
                .extract().response();

        JsonPath shippedJson = shipped.jsonPath();

        assertEquals(id,shippedJson.getInt("id"));
        assertEquals("SHIPPED",shippedJson.getString("status"));
        assertEquals("API Fulfilment Queue", shippedJson.getString("address"));
        assertEquals("Standard", shippedJson.getString("deliverySlot"));
        assertEquals("svc-retail-ops", shippedJson.getString("userId"));

    }

}
