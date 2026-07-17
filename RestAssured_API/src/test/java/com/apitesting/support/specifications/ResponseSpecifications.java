package com.apitesting.support.specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;


public final class ResponseSpecifications {
    private ResponseSpecifications() {
    }

    public static ResponseSpecification ok() {
        return responseWithStatus(200);
    }

    public static ResponseSpecification created() {
        return responseWithStatus(201);
    }

    public static ResponseSpecification unauthorized() {
        return responseWithStatus(401);
    }

    private static ResponseSpecification responseWithStatus(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(ContentType.JSON)
                .build();
    }
}
