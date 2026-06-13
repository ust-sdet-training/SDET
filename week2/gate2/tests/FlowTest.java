package week2.gate2.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import week2.gate2.config.DatabaseConfig;
import week2.gate2.model.OrderRow;
import week2.gate2.repository.Repository;
import week2.gate2.support.DbSupport;
import week2.gate2.support.TestEnvironment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import week2.gate2.config.AuthConfig;

import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static week2.gate2.factory.SpecFactory.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class FlowTest {
    private static final List<Long> createdOrderIds = new ArrayList<>();
    public static String token;
    private static final String BASE_URL = System.getProperty("baseUrl",
            System.getenv().getOrDefault("BASE_URL", "http://localhost:4000"));
    DatabaseConfig config = DatabaseConfig.fromEnvironment();

    DbSupport dbSupport = new DbSupport(config);
    Repository database = new Repository(dbSupport);


    @BeforeAll
    static void setup(){
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";
        token = getToken(AuthConfig.OPS_CLIENT_ID, AuthConfig.OPS_CLIENT_SECRET);
    }

    @AfterEach
    void cleanCreatedRows() throws Exception{
        for(long orderId : createdOrderIds){
            database.deleteOrder(orderId);
        }
        createdOrderIds.clear();
    }

    @Test
    @DisplayName("M1: MySql is reachable through JDBC")
    void localMySqlIsReachable() throws Exception {
        assertTrue(dbSupport.isReachable());
    }

    @Test
    @DisplayName("M2: Api check for order creation")
    void createOrder(){
        var cartItem = Map.of("productId", 101, "quantity", 2);

        given()
                .spec(cartSpec(token))
                .body(cartItem)
                .when()
                .post("/items")
                .then()
                .statusCode(201);


        var orderBody = Map.of(
                "address", "UST Campus, Bengaluru", "paymentMethod", "Credit card", "shipping", 199, "discount", 0);

        String orderId = given()
                .spec(orderSpec(token))
                .body(orderBody)
                .when()
                .post("")
                .then()
                .statusCode(201)
                .extract()
                .path("id").toString();

        given()
                .header("Authorization", "Bearer " + token)
                .pathParam("id", orderId)
                .when()
                .get("/orders/{id}")
                .then()
                .statusCode(200)
                .body("id",      equalTo(Integer.parseInt(orderId)))
                .body("address", equalTo("UST Campus, Bengaluru"));
    }
    @Test
    @DisplayName("M3: Create an order, allocate and fetch")
    void createTo_shipOrder() throws Exception{

        Response c =  given()
                .spec(authedOrders(token))
                .body(Map.of(
                        "items", List.of(101,107),
                        "currency", "INR"
                ))
                .when()
                .post("")
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/json/secure-order.schema.json"))
                .statusCode(201)
                .extract().response();

        long id = c.jsonPath().getLong("orderId");
        createdOrderIds.add(id);

        OrderRow row = database.findOrder(id);
        assertNotNull(row,"order must be persisted");
        assertEquals("CREATED", row.status());
        assertEquals(0,row.total().compareTo(
                c.jsonPath().getObject("total", BigDecimal.class)
        ));

        String status = given()
                .spec(authedOrders(token))
        .when()
                .post("{id}/allocate",id)
        .then()
                .spec(okJson)
                .body(matchesJsonSchemaInClasspath("schemas/json/secure-order.schema.json"))
                .extract()
                .path("status");

        assertEquals("ALLOCATED",status);
        assertNotNull(row,"order must be persisted");
        assertEquals(0,row.total().compareTo(
                c.jsonPath().getObject("total", BigDecimal.class)
        ));

        status = given()
                .spec(authedOrders(token))
                .when()
                .post("{id}/ship",id)
                .then()
                .spec(okJson)
                .body(matchesJsonSchemaInClasspath("schemas/json/secure-order.schema.json"))
                .extract()
                .path("status");

        assertEquals("SHIPPED", status);
        assertNotNull(row,"order must be persisted");
        assertEquals(0,row.total().compareTo(
                c.jsonPath().getObject("total", BigDecimal.class)
                ));

        status = given()
                .spec(authedOrders(token))
                .when()
                .get("{id}",id)
                .then()
                .spec(okJson)
                .body(matchesJsonSchemaInClasspath("schemas/json/secure-order.schema.json"))
                .extract()
                .path("status");
        assertEquals("SHIPPED", status);

        OrderRow finalRow = database.findOrder(id);
        assertEquals("SHIPPED", finalRow.status());
        assertEquals(0,finalRow.total().compareTo(
                c.jsonPath().getObject("total", BigDecimal.class)
        ));


    }

    @Test
    @DisplayName("M4: Legacy product XML matches product XSD")
    void productXml_matchesXsd(){
        given()
                .spec(legacyProductsXml())
                .when()
                .get("/101.xml")
                .then()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body(matchesXsdInClasspath("schemas/xsd/product.xsd"));
    }


}

