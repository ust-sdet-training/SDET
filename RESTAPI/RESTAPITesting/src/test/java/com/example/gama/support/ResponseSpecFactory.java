package com.example.gama.support;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public final class ResponseSpecFactory {

    private ResponseSpecFactory(){}

    public static ResponseSpecification validate201Response(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(201)
                .expectBody(matchesJsonSchemaInClasspath("schemas/post.schema.json"))
                .build();
    }

    public static ResponseSpecification validate204Response(){
        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .expectBody(equalTo(""))
                .build();

    }

    public static ResponseSpecification validate200Response(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectBody(matchesJsonSchemaInClasspath("schemas/get.schema.json"))
                .build();
    }

    public static ResponseSpecification validate200ResponseofPut(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectBody(matchesJsonSchemaInClasspath("schemas/put.schema.json"))
                .build();
    }


    public static ResponseSpecification validate401Response(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(401)
                .expectBody(matchesJsonSchemaInClasspath("schemas/get.schema.json"))
                .build();
    }

    public static ResponseSpecification validate403Response(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(403)
                .expectBody(matchesJsonSchemaInClasspath("schemas/get.schema.json"))
                .build();
    }

    public static ResponseSpecification validate404Response(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(404)
                .expectBody(matchesJsonSchemaInClasspath("schemas/get.schema.json"))
                .build();
    }

    public static ResponseSpecification validate409Response(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(409)
                .expectBody(matchesJsonSchemaInClasspath("schemas/get.schema.json"))
                .build();
    }
}
