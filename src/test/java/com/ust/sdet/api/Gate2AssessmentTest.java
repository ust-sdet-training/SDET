package com.ust.sdet.api;

import com.ust.sdet.api.apiframework.auth.TokenManager;
import com.ust.sdet.api.apiframework.config.ConfigReader;
import com.ust.sdet.api.apiframework.specs.SpecFactory;
import com.ust.sdet.api.apiframework.support.ApiSupport;
import com.ust.sdet.api.apiframework.support.NewOrderFixture;
import com.ust.sdet.api.dbframework.config.DatabaseConfig;
import com.ust.sdet.api.dbframework.model.OrderRow;
import com.ust.sdet.api.dbframework.support.DbSupport;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

public class Gate2AssessmentTest {

    private String getAccessToken() {
        String secret = ConfigReader.get("OAUTH_CLIENT_SECRET", "ops-secret");
        return TokenManager.getToken("retail-ops-client", secret);
    }

    @Test
    @DisplayName("Full happy path lifecycle!")
    void full_happy_path_and_db_reconciliation() throws Exception {
        String token = getAccessToken();
        Map<String, Object> newOrder = NewOrderFixture.newOrder();

        String userToken = ApiSupport.loginAndGetToken();

        Response clearResp = ApiSupport.clearCart(userToken);
        assertEquals(204, clearResp.statusCode());

        Response addResp = ApiSupport.addItemToCart(userToken, Map.of(
            "productId", 101,
            "quantity", 2,
            "size", "UK 9",
            "color", "Navy",
            "fulfillment", "Home delivery"
        ));
        addResp.then().statusCode(201).body("productId", equalTo(101)).body("quantity", equalTo(2));

        Response userPlaced = ApiSupport.placeOrderAsUser(userToken, Map.of(
            "paymentMethod", "Credit Card",
            "deliverySlot", "Tomorrow 10AM - 1 PM",
            "address", "123 Main St, Anytown, USA",
            "shipping", 49,
            "discount", 0
        ));
        userPlaced.then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("items.size()", equalTo(1))
            .body("items[0].productId", equalTo(101))
            .body("items[0].quantity", equalTo(2));

        Response create = ApiSupport.createOrder(newOrder, token);
        assertEquals(201, create.statusCode());
        create.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/json/order.schema.json"));
        long id = create.jsonPath().getLong("id");
        assertTrue(id > 0);
        System.out.println(id);

        DbSupport db = new DbSupport(DatabaseConfig.fromEnvironment());
        try {
            OrderRow row = DbSupport.findOrder(id);
            assertNotNull(row, "DB row must exist after create");
            assertEquals("CREATED", row.status());
            assertEquals(0, row.total().compareTo(create.jsonPath().getObject("total", BigDecimal.class)));

            Response alloc = ApiSupport.allocateOrder(id, token);
            assertEquals(200, alloc.statusCode());

            OrderRow row2 = DbSupport.findOrder(id);
            assertNotNull(row2);
            assertEquals("ALLOCATED", row2.status());

            Response ship = ApiSupport.shipOrder(id, token);
            assertEquals(200, ship.statusCode());

            OrderRow row3 = DbSupport.findOrder(id);
            assertNotNull(row3);
            assertEquals("SHIPPED", row3.status());

            given()
                    .spec(SpecFactory.secureOrders(token))
                    .when()
                    .get("/{id}", id)
                    .then()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath("schemas/json/order.schema.json"));

            Response fetch = ApiSupport.fetchOrder(id, token);
            assertEquals("SHIPPED", fetch.jsonPath().getString("status"));
            BigDecimal apiTotal = fetch.jsonPath().getObject("total", BigDecimal.class);
            OrderRow finalRow = DbSupport.findOrder(id);
            assertEquals(0, finalRow.total().compareTo(apiTotal));

        } finally {
            db.deleteOrder(id);
        }
    }

    @Test
    @DisplayName("Negative:- missing token 401 - secure create")
    void missingTokenReturns401() {
        Map<String, Object> newOrder = NewOrderFixture.newOrder();

        given()
                .spec(SpecFactory.secureOrdersNoAuth())
                .body(newOrder)
                .when()
                .post()
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Negative:- viewer token - create (403)")
    void viewerTokenCannotCreateOrders() {
        String viewer = TokenManager.getToken("retail-viewer-client", ConfigReader.get("OAUTH_VIEWER_CLIENT_SECRET", "viewer-secret"));
        Map<String, Object> newOrder = NewOrderFixture.newOrder();

        given()
                .spec(SpecFactory.secureOrders(viewer))
                .body(newOrder)
                .when()
                .post()
                .then()
                .statusCode(403);
    }

    @Test
    @DisplayName("Negative:- fetch absent id - returns 404")
    void fetchNonExistentReturns404() {
        String token = getAccessToken();
        long missing = 9_999_999L;

        given()
                .spec(SpecFactory.secureOrders(token))
                .when()
                .get("/{id}", missing)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Negative:- ship without allocate - returns 409")
    void shipWithoutAllocateReturns409() throws Exception {
        String token = getAccessToken();
        Map<String, Object> newOrder = NewOrderFixture.newOrder();

        Response create = ApiSupport.createOrder(newOrder, token);
        assertEquals(201, create.statusCode());
        long id = create.jsonPath().getLong("id");

        DbSupport db = new DbSupport(DatabaseConfig.fromEnvironment());
        try {
            Response ship = ApiSupport.shipOrder(id, token);
            assertEquals(409, ship.statusCode());
        } finally {
            db.deleteOrder(id);
        }
    }
}
