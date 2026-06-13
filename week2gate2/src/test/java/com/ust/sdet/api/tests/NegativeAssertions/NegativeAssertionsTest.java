package com.ust.sdet.api.tests.NegativeAssertions;

import com.ust.sdet.api.API_FrameWork.Factory.SpecFactory;
import com.ust.sdet.api.API_FrameWork.config.ApiConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


import java.util.List;
import java.util.Map;

public class NegativeAssertionsTest {

    private static final String BASE_URL = ApiConfig.BASE_URL;

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

    }

    @Test
    @DisplayName("missingToken_is401")
    void missingToken_is401() {

        given()

                .when()
                .get("/orders/5001")

                .then()
                .log().all()
                .spec(SpecFactory.unauthorized401());
    }
    @Test
    @DisplayName("invalidToken_is401")
    void invalidToken_is401() {

        given()
                .auth()
                .oauth2("garbage-token")

                .when()
                .get("/orders/5001")

                .then()
                .log().all()
                .spec(SpecFactory.unauthorized401());
    }
    @Test
    @DisplayName("Checking with Wrong Role")
    void wrongRole_is403() {

        given()
                .auth()
                .oauth2(SpecFactory.getViewerToken())
                .body(Map.of(
                        "items", List.of(101),
                        "currency", "INR"
                ))

                .when()
                .post("/secure/orders")

                .then()
                .spec(SpecFactory.forbidden403());
    }
    @Test
    @DisplayName("Creating a order with expired token")
    void expired_token() {

        given()
                .spec(SpecFactory.expiredWrite())
                .body(Map.of(
                        "items", List.of(101),
                        "currency", "INR"
                ))

                .when()
                .post(SpecFactory.ordersPath())

                .then()
                .spec(SpecFactory.unauthorized401());
    }
    @Test
    @DisplayName("Checking Unknown Order")
    void unknown_order() {

        given()
                .spec(SpecFactory.opsRead())

                .when()
                .get(SpecFactory.secureOrderById(999999))

                .then()
                .spec(SpecFactory.notFound404());
    }

    @Test
    @DisplayName("Conflict check status_409")
    void test_status_409() {

        var item = Map.of(
                "productId", 103,
                "quantity", 1,
                "size", "Standard",
                "color", "Black",
                "fulfilment", "Home delivery"
        );
        var order = Map.of(
                "paymentMethod", "Credit card",
                "deliverySlot", "Tomorrow 9 AM - 12 PM",
                "address", "UST Campus, Bengaluru"
        );

        given()
                .spec(SpecFactory.opsWrite())
                .body(item)

                .when()
                .post(SpecFactory.cartItemsPath())

                .then()
                .statusCode(anyOf(equalTo(200),equalTo(201),equalTo(409)));



        given()
                .spec(SpecFactory.opsWrite())
                .body(order)

                .when()
                .post(SpecFactory.secureOrdersPath())

                .then()
                .log().all()
                .statusCode(201);

    }
}
