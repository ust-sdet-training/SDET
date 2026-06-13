package com.ust.sdet.api.tests;
import com.ust.sdet.api.config.DatabaseConfig;
import com.ust.sdet.api.model.OrderRow;
import com.ust.sdet.api.support.DbSupport;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.ust.sdet.api.specificationfactory.SpecFactory.*;
import static com.ust.sdet.api.specificationfactory.SpecFactory.getToken;
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
    @DisplayName("Local M1:MySql is reachable through JDBC")
    void localMySqlIsReachable() throws Exception {
        assertTrue(database.isReachable());
    }


    @Test
    @DisplayName(("M2: order persists with status ,total,owner, and timestamp"))
    void createOrder_isPersisted_Valid_user() throws Exception {
        Map<String, Object> newOrder = Map.of(
                "items", List.of(106, 112),
                "currency", "INR"
        );
        Response response = given()
                .spec(authedOrderCreatedRequest)
                .auth().oauth2(getToken(System.getenv("OAUTH_CLIENT_ID"), System.getenv("OAUTH_CLIENT_SECRET")))
                .body(newOrder)
                .when().post()
                .then()
                .spec(commonJsonCreateResponse)
                .extract().response();

        long id = response.jsonPath().getLong("orderId");


        OrderRow row = database.findOrder(id);
        assertNotNull(row, "order must be persisted");
        assertEquals("CREATED", row.status());
        assertEquals(0, row.total().compareTo(response.jsonPath().getObject("total", BigDecimal.class)));
    }



    @Test
    @DisplayName("M3: validate user can view the order ")
    void authenticate_with_spec() {
        var response = given()
                .spec(commonJsonRequest)
                .auth().oauth2(getToken(System.getenv("OAUTH_VIEWER_CLIENT_ID"), System.getenv("OAUTH_VIEWER_CLIENT_SECRET")))
                .pathParam("id", 5001)
                .when()
                .get("/secure/orders/{id}")
                .then()
                .spec(commonJsonFetchResponse)
                .body("id", notNullValue())
                .body("status", equalTo("Confirmed"))
                .extract().response();


                JsonPath json=response.jsonPath();
                        assertThat(json.getInt("id"), equalTo(5001));
                        assertThat(json.getInt("orderId"), equalTo(5001));

                        assertThat(json.getString("status"), equalTo("Confirmed"));
                        assertThat(json.getString("payment"), equalTo("Paid"));
                        assertThat(json.getString("paymentMethod"), equalTo("Credit card"));
                        assertThat(json.getString("channel"), equalTo("API"));
                        assertThat(json.getInt("subtotal"), equalTo(8998));
                        assertThat(json.getInt("shipping"), equalTo(49));
                        assertThat(json.getInt("discount"), equalTo(0));
                        assertThat(json.getInt("total"), equalTo(9047));
                        assertThat(json.getString("address"), equalTo("UST Training Lab, Bengaluru"));
                        assertThat(json.getString("deliverySlot"), equalTo("Tomorrow 10 AM - 1 PM"));
    }


    //view can only view the post
    @Test
    @DisplayName("M4: view can only view the post basic along with spec ,status 200 ")
    void viewer_only_fetch_order() {
        var response = given()
                .spec(commonJsonRequest)
                .auth().oauth2(getToken(System.getenv("OAUTH_VIEWER_CLIENT_ID"), System.getenv("OAUTH_VIEWER_CLIENT_SECRET")))
                .pathParam("id", 5001)
                .when()
                .get("/secure/orders/{id}")
                .then()
                .spec(commonJsonFetchResponse)
                .body("id", notNullValue())
                .body("status", equalTo("Confirmed"))
                .extract().response();

        JsonPath json=response.jsonPath();

        assertThat(json.getInt("id"), equalTo(5001));
        assertThat(json.getInt("orderId"), equalTo(5001));
        assertThat(json.getString("status"), equalTo("Confirmed"));
        assertThat(json.getString("payment"), equalTo("Paid"));
        assertThat(json.getString("paymentMethod"), equalTo("Credit card"));
        assertThat(json.getString("channel"), equalTo("API"));
        assertThat(json.getString("address"), equalTo("UST Training Lab, Bengaluru"));
        assertThat(json.getString("deliverySlot"), equalTo("Tomorrow 10 AM - 1 PM"));

    }

    //View customer cant create order
    @Test
    @DisplayName(("M5: View cant post the order so we get 403"))
    void createOrder_isPersisted_View_user() throws Exception {
        Map<String, Object> newOrder = Map.of(
                "items", List.of(106, 112),
                "currency", "INR"
        );
        Response response = given()
                .spec(authedOrderCreatedRequest)
                .auth().oauth2(getToken(System.getenv("OAUTH_VIEWER_CLIENT_ID"), System.getenv("OAUTH_VIEWER_CLIENT_SECRET")))
                .body(newOrder)
                .when().post()
                .then()
                .spec(commonJsonForbiddenResponse)
                .extract()
                .response();

        assertEquals("OPS role required", response.jsonPath().getString("message"));
    }


    //invalid token or without token cant we get to create 401
    @Test
    @DisplayName("M6:  or without token cant we get to create order ,status 401")
    void getOrder_WithoutToken_user_Test()
    {
        var response= given()
                .spec(commonJsonRequest)
                .pathParam("id",5001)
                .when()
                .get("/secure/orders/{id}")
                .then()
                .spec(commonJsonUnAuthoriseResponse)
                .extract().
                response();

        assertEquals("Authentication required", response.jsonPath().getString("message"));
    }

    //invalid token or without token cant we get to create 401

    @Test
    @DisplayName("M7: invalid token  token cant we get to create order ,status 401")
    void getOrder_Invalid_Token_user_Test()
    {
        var response= given()
                .spec(commonJsonRequest)
                .pathParam("id",5001)
                .when()
                .get("/secure/orders/{id}")
                .then()
                .spec(commonJsonUnAuthoriseResponse)
                .extract().
                response();

        assertEquals("Authentication required", response.jsonPath().getString("message"));
    }

    // invalid token with valid role to create order ,status 401 unauthorize
    @Test
    @DisplayName("M8: exercise 3 for auth with basic with expire  token, status 401")
    void getOrder_Expire_token_Test()
    {

        var response =  given()
                .spec(commonJsonRequest)
                .auth().oauth2(getToken(System.getenv("OAUTH_EXPIRED_CLIENT_ID"), System.getenv("OAUTH_EXPIRED_CLIENT_SECRET")))
                .pathParam("id",5001)
                .when()
                .get("/secure/orders/{id}")
                .then()
                .spec(commonJsonUnAuthoriseResponse)
                .extract().
                response();

        assertThat(response.jsonPath().getString("message"),equalTo("Access token expired"));
    }

    @Test
    @DisplayName("M9:Delete by client user status 204 and verify status 404")
    void delete_by_client_user()throws  Exception
    {
        Map<String, Object> newOrder = Map.of(
                "items", List.of(108, 109),
                "currency", "INR"
        );
        Response response = given()
                .spec(authedOrderCreatedRequest)
                .auth().oauth2(getToken(System.getenv("OAUTH_CLIENT_ID"), System.getenv("OAUTH_CLIENT_SECRET")))
                .body(newOrder)
                .when().post()
                .then()
                .spec(commonJsonCreateResponse)
                .extract().response();

        long id =response.jsonPath().getLong("id");

        given()
                .spec(commonJsonRequest)
                .auth().oauth2(getToken(System.getenv("OAUTH_CLIENT_ID"), System.getenv("OAUTH_CLIENT_SECRET")))
                .when()
                .delete("/secure/orders/{id}", id)
                .then()
                .spec(commonJsonDeleteResponse)
                .extract().response();


        // Verify Deleted
        var verifyDelete = given()
                .spec(commonJsonRequest)
                .auth().oauth2(getToken(System.getenv("OAUTH_CLIENT_ID"), System.getenv("OAUTH_CLIENT_SECRET")))
                .when()
                .get("/api/secure/orders/{id}", id)
                .then()
                .spec(commonJsonNotFountResponse)
                .extract()
                .response();
    }
}
