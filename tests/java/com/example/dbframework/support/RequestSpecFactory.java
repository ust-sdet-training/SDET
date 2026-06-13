package com.example.dbframework.support;

import com.example.dbframework.model.OrderRow;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;

public final class RequestSpecFactory {

    private static final List<Long> createdOrderIds = Collections.synchronizedList(new ArrayList<Long>());

    private RequestSpecFactory() {
    }

    private static final String BASE_URL = System.getProperty(
            "baseUrl",
            System.getenv().getOrDefault("BASE_URL", "http://localhost:4000")
    );


    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "api/";
    }

    static RequestSpecification writespec(String basepath, String token) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath(basepath)
                .setAuth(oauth2(token))
                .setContentType(ContentType.JSON)
                .build();
    }

    public static String fetchToken(String name, String pass) {
        return given().baseUri(BASE_URL)
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .auth().preemptive().basic(name, pass)
                .formParam("grant_type", "client_credentials")
                .when()
                .post("/api/oauth/token")
                .then()
                .extract().path("access_token");
    }

    public static RequestSpecification getOrders(String token) {
        return writespec("/api/secure/orders", token);
    }

    public static RequestSpecification getOrderswithnoauth() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api/secure/orders")
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification fetchwithAPIkey(String key) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api/partner/orders")
                .addHeader("X-API-Key", key)
                .build();

    }

    public static RequestSpecification noAPIkey() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api/partner/orders")
                .build();
    }



    public static RequestSpecification authedOrder(String token){

          return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api/secure/orders")
                .setAuth(oauth2(token))
                .setContentType(ContentType.JSON)
                .build();

    }

    public static OrderRow support(Response response) throws SQLException {
        Integer id = response.path("orderId");
        createdOrderIds.add(Long.valueOf(id));

        OrderRow row =DbSupport.findOrder(id);
        return row;
    }


    public static String config(String name, String fallback) {
        String v = System.getProperty(name);

        if (v != null && !v.isBlank()) {

            return v;
        }

        String env = System.getenv(name);
        return (env != null && !env.isBlank()) ? env : fallback;
    }
}
