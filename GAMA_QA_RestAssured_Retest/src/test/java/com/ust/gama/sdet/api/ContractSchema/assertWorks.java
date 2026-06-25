package com.ust.gama.sdet.api.ContractSchema;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.reset;

public class assertWorks {
    public static void assertOkJsonContract(Response response, String schemaPath) {
        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath(schemaPath));

    }
    public static void assertOkXsdContract(Response response, String schemaPath){
        response.then()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body(matchesXsdInClasspath(schemaPath));
    }
}
