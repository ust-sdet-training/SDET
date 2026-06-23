package com.gamagate1.support;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ResponceSpec {
    static public ResponseSpecification response200 = new ResponseSpecBuilder()
                                                .expectStatusCode(200)
                                                .expectContentType(ContentType.JSON)
                                                .expectBody(matchesJsonSchemaInClasspath("schemas/json/pet.schema.json"))
                                                .build();
    
    static public ResponseSpecification response201 = new ResponseSpecBuilder()
                                                .expectStatusCode(201)
                                                .expectContentType(ContentType.JSON)
                                                .expectBody(matchesJsonSchemaInClasspath("schemas/json/pet.schema.json"))
                                                .build();


    static public ResponseSpecification response404 = new ResponseSpecBuilder()
                                                            .expectStatusCode(404)
                                                            .expectContentType(ContentType.JSON)
                                                            .build();
}
