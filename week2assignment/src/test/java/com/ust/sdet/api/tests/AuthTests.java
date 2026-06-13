package com.ust.sdet.api.tests;

import com.ust.sdet.api.config.Config;
import com.ust.sdet.api.dbframework.config.DatabaseConfig;
import com.ust.sdet.api.dbframework.model.OrderRow;
import com.ust.sdet.api.dbframework.support.DbSupport;
import com.ust.sdet.api.specs.SpecFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthTests {

   private static RequestSpecification authed;
    private static DbSupport database;
    private static String Oauth_token(String clientId, String clientSecret){

        return given()
                .baseUri(Config.BASE_URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .auth()
                .preemptive()
                .basic(clientId, clientSecret)
                .formParam("grant_type", "client_credentials")
                .when()
                .post("/api/oauth/token")
                .then()
                .log().all()
                .statusCode(200)
                .body("token_type", equalToIgnoringCase("Bearer"))
                .body("expires_in", greaterThan(0))
                .body("access_token", not(emptyOrNullString()))
                .extract()
                .path("access_token");
    }
    @BeforeAll
    static void setup() {
            String token = Oauth_token(Config.CLIENT_ID, Config.CLIENT_SECRET);
            authed = com.ust.sdet.api.specs.SpecFactory.authed(token);

        database = new DbSupport(DatabaseConfig.fromEnvironment());
        }
    @Test
    @DisplayName("Create order using authenticated spec")
    void createOrder() {

        Response response =
                given()
                        .spec(authed)
                        .body(Map.of(
                                "items", List.of(101, 107),
                                "currency", "INR"
                        ))
                        .when()
                        .post("/api/secure/orders")
                        .then()
                        .spec(SpecFactory.created201())
                        .body("id", notNullValue())
                        .body("items.size()", equalTo(2))
                        .body("orderNumber", notNullValue())
                        .body("payment", equalTo("Pending"))
                        .body("channel", equalTo("API"))
                        .body("subtotal", greaterThan(0))
                        .body("total", greaterThan(0))
                        .body(matchesJsonSchemaInClasspath(
                                "Schema/order.schema.json"))
                        .extract()
                        .response();

        System.out.println(response.asPrettyString());
    }

    @Test
    @DisplayName("Get order using Auth")
    void getOrder() {
Response response=
        given()
                .spec(authed)
                .when()
                .get("/api/secure/orders/{id}", 6001)
                .then()
                .spec(SpecFactory.ok200())
                .body("id", equalTo(6001))
                .body("status", equalTo("CREATED"))
                .body("orderNumber", notNullValue())
                .body("items.size()", greaterThan(0))
                .body("channel", equalTo("API"))
                .body("total", greaterThan(0))
                        .extract().response();

        System.out.println(response.asString());
    }
    @Test
    @DisplayName("404 Not found")
    void pagenotfound() {

        given()
                .spec(authed)
                .when()
                .get("/api/secure/orders/{id}", 9999)
                .then()
                .statusCode(404);
    }
    @Test
    @DisplayName("Delete created order returns 204")
    void deleteOrderReturns204() {

        // Create order
        Response createResponse =
                given()
                        .spec(authed)
                        .body(Map.of(
                                "items", List.of(101, 107),
                                "currency", "INR"
                        ))
                        .when()
                        .post("/api/secure/orders")
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        long orderId = createResponse.jsonPath().getLong("id");

        // Delete order
        given()
                .spec(authed)
                .when()
                .delete("/api/secure/orders/" + orderId)
                .then()
                .statusCode(204);

        // Verify order no longer exists
        given()
                .spec(authed)
                .when()
                .get("/api/secure/orders/" + orderId)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Exceed max cart quantity returns 409")
    void conflict409() {

        // clean the cart
        given()
                .spec(authed)
                .when()
                .delete("/api/cart")
                .then()
                .statusCode(204);

        given()
                .spec(authed)
                .body(Map.of(
                        "productId", 104,
                        "quantity", 4
                ))
                .when()
                .post("/api/cart/items")
                .then()
                .statusCode(201)
                .body("quantity", equalTo(4))
                .body("product.id", equalTo(104));

        given()
                .spec(authed)
                .body(Map.of(
                        "productId", 104,
                        "quantity", 2
                ))
                .when()
                .post("/api/cart/items")
                .then()
                .statusCode(409)
                .body("message",
                        equalTo("Maximum quantity per cart line is 5"));

        // Cleanup
        given()
                .spec(authed)
                .when()
                .delete("/api/cart")
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("With no token returns 401")
    void noToken() {

        given()
                .spec(SpecFactory.unauthSpec())
                .when()
                .get("/api/secure/orders/{id}", 6002)
                .then()
                .spec(SpecFactory.unauthorized401())
                .log().all();

    }

    @Test
    @DisplayName("Invalid token returns 401")
    void invalidToken() {

        given()
                .spec(SpecFactory.authed("garbage"))
                .when()
                .get("/api/secure/orders/{id}", 6024)
                .then()
                .spec(SpecFactory.unauthorized401());
    }

    @Test
    @DisplayName("Viewer role returns 403")
    void forbiddenRole() {

        String viewerToken = Oauth_token(Config.RETAIL_CLIENT_ID, Config.RETAIL_CLIENT_SECRET);
        RequestSpecification viewerSpec = SpecFactory.authed(viewerToken);
Response forbidden=
        given()
                .spec(viewerSpec)
                .body(Map.of(
                        "items", List.of(101, 107)
                ))
                .when()
                .post("/api/secure/orders")
                .then()
                .spec(SpecFactory.forbidden403())
                .body("message", equalTo("OPS role required"))
                .extract().response();
        System.out.println(forbidden.asPrettyString());
    }

    @Test
    void fetchWithApiKey() {
        given()
                .spec(SpecFactory.oauthSpec())
                .header("X-API-Key", Config.retail_demo_key)
                .when().get("/api/partner/orders/{id}", 5001)
                .then().statusCode(200);
    }
    @Test
    @DisplayName("Order lifecycle - Create to Delete")
    void orderLifecycleWithAllocateAndShip() throws SQLException {

        // Create Order
        Response createResponse =
                given()
                        .spec(authed)
                        .body(Map.of(
                                "items", List.of(101, 107)
                        ))
                        .when()
                        .post("/api/secure/orders")
                        .then()
                        .statusCode(201)
                        .body("id", notNullValue())
                        .body("status", equalTo("CREATED"))
                        .body("items.size()", equalTo(2))
                        .body("total", greaterThan(0))
                        .extract()
                        .response();

        long orderId = createResponse.jsonPath().getLong("id");
        OrderRow createdRow = database.findOrder(orderId);

        assertNotNull(createdRow, "Created order should exist in DB");
        assertEquals(orderId, createdRow.id());
        assertEquals("CREATED", createdRow.status());

        // Fetch Created Order
        given()
                .spec(authed)
                .when()
                .get("/api/secure/orders/{id}", orderId)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo((int) orderId))
                .body("status", equalTo("CREATED"));

        // Allocate Order
        given()
                .spec(authed)
                .when()
                .post("/api/secure/orders/{id}/allocate", orderId)
                .then()
                .statusCode(200)
                .log().all()
                .body("id", equalTo((int) orderId))
                .body("status", equalTo("ALLOCATED"));

        OrderRow allocatedRow = database.findOrder(orderId);

        assertNotNull(allocatedRow);
        assertEquals("ALLOCATED", allocatedRow.status());

        // Verify Allocated
        given()
                .spec(authed)
                .when()
                .get("/api/secure/orders/{id}", orderId)
                .then()
                .statusCode(200)
                .body("status", equalTo("ALLOCATED"));

        // Ship Order
        given()
                .spec(authed)
                .when()
                .post("/api/secure/orders/{id}/ship", orderId)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo((int) orderId))
                .body("status", equalTo("SHIPPED"));

        // DB Validation - Shipped
        OrderRow shippedRow = database.findOrder(orderId);

        assertNotNull(shippedRow);
        assertEquals("SHIPPED", shippedRow.status());

        // Verify Shipped
        given()
                .spec(authed)
                .when()
                .get("/api/secure/orders/{id}", orderId)
                .then()
                .log().all()
                .statusCode(200)
                .body("status", equalTo("SHIPPED"));

        // Delete Order
       given()
                .spec(authed)
                .when()
                .delete("/api/secure/orders/{id}", orderId)
                .then()
                .log().all()
                .statusCode(204)
                        .extract().response();
        // DB Validation - Deleted
        OrderRow deletedRow = database.findOrder(orderId);

        assertNull(deletedRow, "Deleted order should not exist in DB");

        // Verify Deleted
        given()
                .spec(authed)
                .when()
                .get("/api/secure/orders/{id}", orderId)
                .then()
                .log().all()
                .statusCode(404);
    }
}