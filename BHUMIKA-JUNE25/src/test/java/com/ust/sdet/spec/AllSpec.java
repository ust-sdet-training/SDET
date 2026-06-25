package com.ust.sdet.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class AllSpec {
    public static ResponseSpecification successResponse() {

        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification notFoundResponse() {

        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }

}
