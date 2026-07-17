package com.apitesting.support.specifications;

import com.apitesting.config.ApiConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public final class RequestSpecifications {
    private RequestSpecifications() {
    }

    public static RequestSpecification defaultRequest() {
        return new RequestSpecBuilder()
                .setBaseUri(ApiConfig.BASE_URL)
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static RequestSpecification authenticatedRequest(String token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(defaultRequest())
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
