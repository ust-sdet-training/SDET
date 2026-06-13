package com.week2_gate2.apiframework.support;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;

import io.restassured.builder.RequestSpecBuilder.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder.*;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import com.week2_gate2.apiframework.config.*;;

public class APISupport {

    public static RequestSpecification authedOrders(String token) {
        return new RequestSpecBuilder()
                    .setBaseUri(ApiConfig.BASE_URL)
                    .setBasePath("api/secure/orders")
                    .setAccept(ContentType.JSON)
                    .setContentType(ContentType.JSON)
                    .setAuth(oauth2(token))
                    .build();
    }

    static RequestSpecification retailKey(String API_KEY) {
        return new RequestSpecBuilder()
                    .setBaseUri(ApiConfig.BASE_URL)
                    .setBasePath("/api/partner/orders/")
                    .setAccept(ContentType.JSON)
                    .addHeader("x-api-key", API_KEY)
                    .build();
    }

    public static RequestSpecification missing_authed() {
        return new RequestSpecBuilder()
                    .setBaseUri(ApiConfig.BASE_URL)
                    .setBasePath("api/secure/orders")
                    .setAccept(ContentType.JSON)
                    .build();
    }

    public static String fetchToken(String id, String secret) {
        return given()
            .baseUri(ApiConfig.BASE_URL)
            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
            .auth().preemptive().basic(id, secret)
            .formParam("grant_type", "client_credentials")
        .when()
            .post("/api/oauth/token")
        .then()
            .statusCode(200)
            .body("token_type", equalToIgnoringCase("Bearer"))
            .body("expires_in", greaterThan(0))
            .body("access_token", not(emptyOrNullString()))
            .extract()
            .path("access_token");
    }

    static String fetchExpiredToken(String id, String secret) {
        return given()
            .baseUri(ApiConfig.BASE_URL)
            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
            .auth().preemptive().basic(id, secret)
            .formParam("grant_type", "client_credentials")
        .when()
            .post("/api/oauth/token")
        .then()
            .statusCode(200)
            .body("token_type", equalToIgnoringCase("Bearer"))
            .body("access_token", not(emptyOrNullString()))
            .extract()
            .path("access_token");
    }
}
