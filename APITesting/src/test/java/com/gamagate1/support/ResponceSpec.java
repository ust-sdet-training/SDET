package com.gamagate1.support;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ResponceSpec {

        public static ResponseSpecification response200 =
                new ResponseSpecBuilder()
                        .expectStatusCode(200)
                        .expectContentType(ContentType.JSON)
                        .build();

        public static ResponseSpecification response201 =
                new ResponseSpecBuilder()
                        .expectStatusCode(201)
                        .expectContentType(ContentType.JSON)
                        .build();

        public static ResponseSpecification response404 =
                new ResponseSpecBuilder()
                        .expectStatusCode(404)
                        .expectContentType(ContentType.JSON)
                        .build();
    }


