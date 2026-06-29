package com.ust.sdet.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public final class RequestSpecs {

    private RequestSpecs() {}

    public static RequestSpecification jsonRequestSpec() {
        return new RequestSpecBuilder().setContentType(JSON).setAccept(JSON).build();
    }

    public static RequestSpecification formRequestSpec() {
        return new RequestSpecBuilder().setContentType("application/x-www-form-urlencoded;charset=UTF-8").setAccept(JSON).build();
    }

    public static RequestSpecification xmlRequestSpec() {
        return new RequestSpecBuilder().setContentType(ContentType.XML).setAccept(ContentType.XML).build();
    }

    public static RequestSpecification authenticatedSpec(String token) {
        return new RequestSpecBuilder().setContentType(JSON).setAccept(JSON).addHeader("Authorization", "Bearer " + token).build();
    }

    public static RequestSpecification apiKeySpec(String apiKey) {
        return new RequestSpecBuilder().setAccept(JSON).addHeader("x-api-key",apiKey).build();
    }
}