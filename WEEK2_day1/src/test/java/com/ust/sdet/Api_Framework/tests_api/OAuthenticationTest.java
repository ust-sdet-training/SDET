package com.ust.sdet.Api_Framework.tests_api;

import com.ust.sdet.Api_Framework.specs.SpecFactory;
import com.ust.sdet.Api_Framework.config.ApiConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class OAuthenticationTest {

    private static final String API_KEY = "4bd53b7820525db94f8f34d9316bb35ee1e2ab9fe80e4c3f";

    private static final String BASE_URL = ApiConfig.BASE_URL;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";

    }

    @Test
    void verifyOpsJwtClaims() {

        String token = SpecFactory.getOAuthToken();
        String header = new String(
                Base64.getUrlDecoder().decode(token.split("\\.")[0]),
                StandardCharsets.UTF_8
        );
        String payload = new String(
                Base64.getUrlDecoder().decode(token.split("\\.")[1]),
                StandardCharsets.UTF_8
        );
        System.out.println("HEADER:");
        System.out.println(header);

        System.out.println();

        System.out.println("PAYLOAD:");
        System.out.println(payload);
        assertTrue(payload.contains("\"role\":\"OPS\""));
        assertTrue(payload.contains("orders:write"));
        assertTrue(payload.contains("orders:read"));
    }

    @Test
    @DisplayName("Checking a Valid Order")
    void check_valid_order() {

        given()
                .spec(SpecFactory.viewerRead())
                .when()
                .get(SpecFactory.ordersPath())

                .then()
                .log().all()
                .spec(SpecFactory.ok200());
    }

    @Test
    @DisplayName("Partner order access with API Key")
    void partnerOrderAccess() {

        given()
                .header("X-API-Key", API_KEY)

                .when()
                .get("/partner/orders/5001")

                .then()
                .log().all()
                .spec(SpecFactory.ok200())
                .body("partner", equalTo("UST Partner Channel"))
                .body("order.id", equalTo(5001));
    }
}