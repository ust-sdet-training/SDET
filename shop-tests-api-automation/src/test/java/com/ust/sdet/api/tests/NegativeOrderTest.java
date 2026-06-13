package com.ust.sdet.api.tests;

import com.ust.sdet.api.config.DatabaseConfig;
import com.ust.sdet.api.support.DbSupport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.ust.sdet.api.specification.OrderSpec.orderReqSpec;
import static com.ust.sdet.api.support.Utils.*;
import static io.restassured.RestAssured.given;

public class NegativeOrderTest {
    private static String TOKEN;

    @BeforeAll
    static void setup() {
        TOKEN = token();
    }

    //Conflict
    @Test
    @DisplayName("M1: Wrong status flow 409 conflict")
    void conflictTest409(){
        given()
                .spec(orderReqSpec(TOKEN))
                .when().post("{id}/ship", 5001)
                .then()
                .statusCode(409);
    }

    @Test
    @DisplayName("M2: 401 unauthenticated -> empty token")
    void authenticateTest401() {
        given()
                .spec(orderReqSpec(""))
                .body(Map.of(
                        "items", List.of(101, 107),
                        "currency", "INR"
                ))
                .when()
                .get("/{id}", 5001)
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("M3: 401 garbage token")
    void authenticateTest401_GarbageToken() {
        given()
                .spec(orderReqSpec(TOKEN + "afgartag424tg"))
                .body(Map.of(
                        "items", List.of(101, 107),
                        "currency", "INR"
                ))
                .when()
                .get("/{id}", 5001)
                .then()
                .statusCode(401);


    }

    @Test
    @DisplayName("M4: 401 Expired token")
    void authenticateTest401_ExpiredUser() {
        given()
                .spec(orderReqSpec(expiredToken()))
                .body(Map.of(
                        "items", List.of(101, 107),
                        "currency", "INR"
                ))
                .when()
                .get("/{id}", 5001)
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("M5: 403 authorized")
    void authenticateTest403() {
        given()
                .spec(orderReqSpec(viewerToken()))
                .body(Map.of(
                        "items", List.of(101, 107),
                        "currency", "INR"
                ))
                .when()
                .post()
                .then()
                .statusCode(403);
    }

    //Conflict
    @Test
    @DisplayName("M6: 404 not found test")
    void Test(){
        given()
                .spec(orderReqSpec(TOKEN))
                .when().post("{id}/ship", -1)
                .then()
                .statusCode(404);
    }
}
