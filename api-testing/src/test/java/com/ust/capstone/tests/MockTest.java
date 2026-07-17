package com.ust.capstone.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static io.restassured.RestAssured.given;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.hamcrest.Matchers.equalTo;

import java.net.http.HttpClient;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import io.restassured.RestAssured;


@Epic("tripStack Journeys")
@Feature("Full Checkout Journey")
@Owner("Akhil")
public class MockTest {

    @RegisterExtension
    static WireMockExtension vm = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @BeforeEach
    void setup(){
        RestAssured.baseURI  = vm.baseUrl();
        HttpClient client = HttpClient.newHttpClient();
    }

    @AfterEach
    void cleanup() {
        vm.resetAll();
    }

    @Test
    @DisplayName("payment internal server error")
    void paymentServerError() {

        vm.stubFor(get(urlEqualTo("/mocks.tripstack.doomple.com/payment"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("""
                        {
                          "error":"Internal Server Error",
                          "latency":"500",
                          "timeout":"5000"
                        }
                    """)
                        .withHeader("Content-Type", "application/json")
                ));

        given()
                .when()
                .get("/mocks.tripstack.doomple.com/payment")
                .then()
                .statusCode(500)
                .body("error", equalTo("Internal Server Error"))
                .body("timeout", equalTo("5000"));

        vm.verify(getRequestedFor(
                urlEqualTo("/mocks.tripstack.doomple.com/payment")));
    }

}