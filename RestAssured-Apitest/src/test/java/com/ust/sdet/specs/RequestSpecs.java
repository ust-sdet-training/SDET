package com.ust.sdet.specs;

import com.ust.sdet.config.TestConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public final class RequestSpecs {

    private RequestSpecs() {
    }

    public static RequestSpecification jsonRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static RequestSpecification formRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType("application/x-www-form-urlencoded;charset=UTF-8")
                .setAccept(ContentType.JSON)
                .build();
    }

    public static RequestSpecification xmlRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.XML)
                .setAccept(ContentType.XML)
                .build();
    }

    public static RequestSpecification authenticatedSpec(
            String token
    ) {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setAccept(JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }


    public static RequestSpecification apiKeySpec(
            String apiKey
    ) {
        return new RequestSpecBuilder()
                .setAccept(JSON)
                .addHeader("x-api-key", apiKey)
                .build();
    }

    public static RequestSpecification formparams() {
        return new RequestSpecBuilder()
                .addFormParam("grant_type", TestConfig.GRANT_TYPE)
                .addFormParam("client_id", TestConfig.CLIENT_ID)
                .addFormParam("client_secret", "wrong-secret")
                .build();
    }

    public static RequestSpecification oauthformparams() {
        return new RequestSpecBuilder()
                .addFormParam("grant_type", TestConfig.GRANT_TYPE)
                .addFormParam("client_id", TestConfig.CLIENT_ID)
                .addFormParam("client_secret", TestConfig.CLIENT_SECRET)
                .build();
    }
}