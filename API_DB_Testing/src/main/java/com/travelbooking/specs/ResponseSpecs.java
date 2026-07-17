package com.travelbooking.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public final class ResponseSpecs {

    private ResponseSpecs() {
    }

    public static ResponseSpecification success() {

        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification created() {

        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(201)
                .build();
    }

    public static ResponseSpecification badRequest() {

        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

    public static ResponseSpecification unauthorized() {

        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .build();
    }

    public static ResponseSpecification forbidden() {

        return new ResponseSpecBuilder()
                .expectStatusCode(403)
                .build();
    }

    public static ResponseSpecification paymentRequired() {

        return new ResponseSpecBuilder()
                .expectStatusCode(402)
                .build();
    }

    public static ResponseSpecification serviceUnavailable() {

        return new ResponseSpecBuilder()
                .expectStatusCode(503)
                .build();
    }
}