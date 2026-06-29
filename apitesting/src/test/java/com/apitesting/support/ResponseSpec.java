package com.apitesting.support;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ResponseSpec {
    public static ResponseSpecification getResponse = new ResponseSpecBuilder()
                                        .expectStatusCode(200)
                                        .expectBody(matchesJsonSchemaInClasspath("schemas/json/post.schema.json"))
                                        .expectContentType(ContentType.JSON)
                                        .build();
    
}
