package com.ust.capstone.api;

import com.ust.capstone.config.AppConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public final class ApiSpec {

    private ApiSpec() {
    }

    public static RequestSpecification authedSpec(String token) {

        return new RequestSpecBuilder()
                .setBaseUri(AppConfig.BASE_URL)
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Authorization","Bearer " + token)
                .build();
    }

    public static RequestSpecification requestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri(AppConfig.BASE_URL)
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification okResponse() {

        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification createdResponse() {

        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }
}