package com.example.gama.Specfactory;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ResponseSpec {

    public static ResponseSpecification validate200Response(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectBody(matchesJsonSchemaInClasspath("schemas/get.schema.json"))
                .build();
    }

    public static ResponseSpecification validate201Response(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(201)
                .expectBody(matchesJsonSchemaInClasspath("schemas/get.schema.json"))
                .build();
    }

    public static ResponseSpecification validate200Responseofarray(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectBody(matchesJsonSchemaInClasspath("schemas/get_array.schema.json"))
                .build();
    }


}
