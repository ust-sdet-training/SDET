package com.tripstack.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GatewayTimeoutTest {

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {

        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();

        configureFor("localhost", 8089);

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8089;
    }

    @Test
    void verifyGatewayTimeout() {

        stubFor(get(urlEqualTo("/checkout"))
                .willReturn(aResponse()
                        .withStatus(504)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                  "status": 504,
                                  "message": "Gateway Timeout"
                                }
                                """)));

        given()
                .contentType(ContentType.JSON)

                .when()
                .get("/checkout")

                .then()
                .statusCode(504)
                .body("status", equalTo(504))
                .body("message", equalTo("Gateway Timeout"));
    }

    @AfterAll
    static void tearDown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}