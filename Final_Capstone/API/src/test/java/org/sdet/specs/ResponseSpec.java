package org.sdet.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.http.ContentType.JSON;

public class ResponseSpec {

    public static ResponseSpecification responseSpec() {

        return new ResponseSpecBuilder()
                .expectContentType(JSON)
                .build();
    }

}