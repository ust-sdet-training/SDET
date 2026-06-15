package com.ust.sdet.api.tests;

import com.ust.sdet.api.config.DatabaseConfig;
import com.ust.sdet.api.model.OrderRow;
import com.ust.sdet.api.support.DbSupport;
import com.ust.sdet.api.support.TestEnvironment;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.ust.sdet.api.specificationfactory.SpecFactory.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class JsonApiStatusTest {

    private static DbSupport database;

    @BeforeAll
    static void setup() {
        database = new DbSupport(DatabaseConfig.fromEnvironmentCredential());
    }

    @Test
    @DisplayName("M1: MySQL reachable")
    void localMySqlIsReachable() throws SQLException {
        assertTrue(database.isReachable());
    }

    @Test
    @DisplayName("M2: Order persists")
    void createOrder_isPersisted_Valid_user() throws SQLException {

        Map<String, Object> newOrder = Map.of(
                "items", List.of(106, 112),
                "currency", "INR"
        );

        Response response = given()
                .spec(authedOrderCreatedRequest)
                .auth().oauth2(getToken(
                        TestEnvironment.required("OAUTH_CLIENT_ID"),
                        TestEnvironment.required("OAUTH_CLIENT_SECRET")
                ))
                .body(newOrder)
                .when().post()
                .then()
                .spec(commonJsonCreateResponse)
                .extract().response();

        long id = response.jsonPath().getLong("orderId");

        OrderRow row = database.findOrder(id);

        assertNotNull(row);
        assertEquals("CREATED", row.status());
        assertEquals(0,
                row.total().compareTo(
                        response.jsonPath().getObject("total", BigDecimal.class)
                ));
    }

    @Test
    @DisplayName("M3: Viewer fetch order")
    void authenticate_with_spec() {

        var response = given()
                .spec(commonJsonRequest)
                .auth().oauth2(getToken(
                        TestEnvironment.required("OAUTH_VIEWER_CLIENT_ID"),
                        TestEnvironment.required("OAUTH_VIEWER_CLIENT_SECRET")
                ))
                .pathParam("id", 5001)
                .when()
                .get("/secure/orders/{id}")
                .then()
                .spec(commonJsonFetchResponse)
                .extract().response();

        JsonPath json = response.jsonPath();

        assertThat(json.getInt("id"), equalTo(5001));
        assertThat(json.getString("status"), equalTo("Confirmed"));
    }

    @Test
    @DisplayName("M4: Viewer cannot create (403)")
    void createOrder_isPersisted_View_user() {

        Map<String, Object> newOrder = Map.of(
                "items", List.of(106, 112),
                "currency", "INR"
        );

        Response response = given()
                .spec(authedOrderCreatedRequest)
                .auth().oauth2(getToken(
                        TestEnvironment.required("OAUTH_VIEWER_CLIENT_ID"),
                        TestEnvironment.required("OAUTH_VIEWER_CLIENT_SECRET")
                ))
                .body(newOrder)
                .when().post()
                .then()
                .spec(commonJsonForbiddenResponse)
                .extract().response();

        assertEquals("OPS role required", response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("M5: Without token (401)")
    void getOrder_WithoutToken_user_Test() {

        given()
                .spec(commonJsonRequest)
                .pathParam("id", 5001)
                .when()
                .get("/secure/orders/{id}")
                .then()
                .spec(commonJsonUnAuthoriseResponse);
    }

    @Test
    @DisplayName("M6: Expired token")
    void getOrder_Expire_token_Test() {

        var response = given()
                .spec(commonJsonRequest)
                .auth().oauth2(getToken(
                        TestEnvironment.required("OAUTH_EXPIRED_CLIENT_ID"),
                        TestEnvironment.required("OAUTH_EXPIRED_CLIENT_SECRET")
                ))
                .pathParam("id", 5001)
                .when()
                .get("/secure/orders/{id}")
                .then()
                .spec(commonJsonUnAuthoriseResponse)
                .extract().response();

        assertThat(response.jsonPath().getString("message"),
                equalTo("Access token expired"));
    }

    @Test
    @DisplayName("M7: Delete order")
    void delete_by_client_user() {

        Map<String, Object> newOrder = Map.of(
                "items", List.of(108, 109),
                "currency", "INR"
        );

        Response response = given()
                .spec(authedOrderCreatedRequest)
                .auth().oauth2(getToken(
                        TestEnvironment.required("OAUTH_CLIENT_ID"),
                        TestEnvironment.required("OAUTH_CLIENT_SECRET")
                ))
                .body(newOrder)
                .when().post()
                .then()
                .spec(commonJsonCreateResponse)
                .extract().response();

        long id = response.jsonPath().getLong("orderId");

        // DELETE
        given()
                .spec(commonJsonRequest)
                .auth().oauth2(getToken(
                        TestEnvironment.required("OAUTH_CLIENT_ID"),
                        TestEnvironment.required("OAUTH_CLIENT_SECRET")
                ))
                .when()
                .delete("/secure/orders/{id}", id)
                .then()
                .spec(commonJsonDeleteResponse);

        // VERIFY DELETE
        given()
                .spec(commonJsonRequest)
                .auth().oauth2(getToken(
                        TestEnvironment.required("OAUTH_CLIENT_ID"),
                        TestEnvironment.required("OAUTH_CLIENT_SECRET")
                ))
                .when()
                .get("/secure/orders/{id}", id)
                .then()
                .spec(commonJsonNotFountResponse);
    }
}