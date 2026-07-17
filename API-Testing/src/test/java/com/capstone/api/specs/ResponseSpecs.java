package com.capstone.api.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecs {
    private ResponseSpecs() {}

    public static ResponseSpecification ok200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification created201() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }

    public static ResponseSpecification forbidden403() {
        return new ResponseSpecBuilder()
                .expectStatusCode(403)
                .build();
    }

    public static ResponseSpecification conflict409() {
        return new ResponseSpecBuilder()
                .expectStatusCode(409)
                .build();
    }
}
