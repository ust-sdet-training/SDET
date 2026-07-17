package com.travel.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpec {

    private RequestSpec(){}

    public static RequestSpecification publicRequest() {

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification authRequest(String token) {

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}