package com.ust.sdet.api.apiframework.auth;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.ust.sdet.api.apiframework.config.ConfigManager;
import com.ust.sdet.api.apiframework.spec.AllSpec;
import com.ust.sdet.api.apiframework.testData.ModelObject;
import io.restassured.response.Response;


public class TokenManager {

        public static String getToken(String clientId, String clientSecret) {

            return given()
                    .baseUri(ConfigManager.BASE_URL)
                    .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                    .auth()
                    .preemptive()
                    .basic(clientId, clientSecret)
                    .formParam("grant_type", "client_credentials")

                    .when()
                    .post("/api/oauth/token")

                    .then()
                    .statusCode(200)
                    .extract()
                    .path("access_token");
        }

        public static String getOpsToken() {
            return getToken(
                    ConfigManager.CLIENT_ID,
                    ConfigManager.CLIENT_SECRET
            );
        }

        public static String getViewerToken() {
            return getToken(
                    ConfigManager.VIEWER_CLIENT_ID,
                    ConfigManager.VIEWER_CLIENT_SECRET
            );
        }

        public static String getExpiredToken() {
            return getToken(
                    ConfigManager.EXPIRED_CLIENT_ID,
                    ConfigManager.EXPIRED_CLIENT_SECRET
            );
        }

        //login

        public static String loginAndGetToken() {

            return given()
                    .spec(AllSpec.jsonSpec("/api"))
                    .body(Map.of(
                            "email", "customer@example.com",
                            "password", "Password@123"
                    ))
                    .when()
                    .post("/login")
                    .then()
                    .spec(AllSpec.okJson())
                    .extract()

                    .path("token");
        }

        //
        public static void clearCart(String token) {

            given()
                    .spec(AllSpec.authSpec("/api/cart",
                            token))
                    .when()
                    .delete()
                    .then()
                    .spec(AllSpec.noContent());
        }

        public static void addItem(String token) {

            given()
                    .spec(AllSpec.authSpec(
                            "/api/cart/items",
                            token))
                    .body(Map.of(
                            "productId", 101,
                            "quantity", 2,
                            "size", "UK 9",
                            "color", "Navy",
                            "fulfillment",
                            "Home delivery"
                    ))
                    .when()
                    .post()
                    .then()

                    .spec(AllSpec.createdJson());
        }
        //
        public static Integer createOrder(String token) {

            return given()
                    .spec(AllSpec.userOrders(token))
                    .body(Map.of(
                            "paymentMethod", "Credit Card",
                            "deliverySlot", "Tomorrow",
                            "address", "123 Main St, Anytown, USA",
                            "shipping", 49,
                            "discount", 0,
                            "items", new Object[]{
                                    Map.of(
                                            "productId", 101,
                                            "quantity", 1
                                    )
                            }
                    ))
                    .when()
                    .post()
                    .then()
                    .log().all()
                    .statusCode(201)
                    .body("id", notNullValue())
                    .body("paymentMethod", equalTo("Credit Card"))
                    .body("shipping", equalTo(49))
                    .body("discount", equalTo(0))
                    .body("deliverySlot", equalTo("Tomorrow"))

                    .extract()
                    .path("id");


        }
//allocate
public static Response allocateOrder(String token, Integer id) {

    return given()
            .spec(AllSpec.secureOrders(token))
            .pathParam("id", id)
            .log().all()
            .when()
            .post("/{id}/allocate")
            .then()
            .log().all()
            .body(matchesJsonSchemaInClasspath("schemas/json/ops.schema.json"))
            .body("id", equalTo(id))
            .body("status", equalTo("ALLOCATED"))
            .extract()
            .response();
}
    public static Response shipOrder(String token, Integer id) {

        return given()
                .baseUri(ConfigManager.BASE_URL)
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .when()
                .post("/api/secure/orders/{id}/ship", id)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/json/ops.schema.json"))
                .body("status", equalTo("SHIPPED"))
                .extract()
                .response();
    }

    public static void getSecureOrder(String token, Integer id) {

        given()
                .baseUri(ConfigManager.BASE_URL)
                .header("Authorization", "Bearer " + token)

                .when()
                .get("/api/secure/orders/{id}", id)

                .then()
                . body(matchesJsonSchemaInClasspath(
                "schemas/json/ops.schema.json"))
                .body("id", equalTo(id))
                .log().all();
    }
    public static Integer createSecureOrder(String token) {

        return given()
                .spec(AllSpec.secureOrders(token))
                .body(ModelObject.createOrder())
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath(
                        "schemas/json/ops.schema.json"))
                .body("id", notNullValue())
                .body("status", equalTo("CREATED"))
                .extract()
                .path("id");
    }

    }



