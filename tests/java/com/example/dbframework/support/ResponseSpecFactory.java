package com.example.dbframework.support;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public final class ResponseSpecFactory {

    private ResponseSpecFactory(){}

    public static ResponseSpecification validateOrders(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(201)
                .expectBody(matchesJsonSchemaInClasspath("schemas/order.schema.json"))
                .build();
    }

    public static ResponseSpecification validate200Response(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectBody(matchesJsonSchemaInClasspath("schemas/order.schema.json"))
                .build();
    }

    public static ResponseSpecification validate401Response(){
          return new ResponseSpecBuilder()
                  .expectContentType(ContentType.JSON)
                  .expectStatusCode(401)
                  .expectBody(matchesJsonSchemaInClasspath("schemas/order.schema.json"))
                  .build();
    }

    public static ResponseSpecification validate403Response(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(403)
                .expectBody(matchesJsonSchemaInClasspath("schemas/order.schema.json"))
                .build();
    }

    public static ResponseSpecification validate404Response(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(404)
                .expectBody(matchesJsonSchemaInClasspath("schemas/order.schema.json"))
                .build();
    }

    public static ResponseSpecification validate409Response(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(409)
                .expectBody(matchesJsonSchemaInClasspath("schemas/order.schema.json"))
                .build();
    }
}
