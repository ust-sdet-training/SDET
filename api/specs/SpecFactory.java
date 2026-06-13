package com.ust.sdet.api.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public final class SpecFactory {

    private static final String BASE_URI = "http://localhost:4000";

    private SpecFactory() {
    }

    public static RequestSpecification oauthSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType("application/x-www-form-urlencoded;charset=UTF-8")
                .addFormParam("grant_type", "client_credentials")
                .build();
    }

    public static RequestSpecification authed(String token) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }

    public static RequestSpecification unauthSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification ok200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification created201() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification unauthorized401() {
        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .build();
    }

    public static ResponseSpecification forbidden403() {
        return new ResponseSpecBuilder()
                .expectStatusCode(403)
                .build();
    }
    public static RequestSpecification req() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.XML)
                .build();
    }
}