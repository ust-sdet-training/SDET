package com.tripstack.utils;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public final class JsonSchemaValidator {

    private JsonSchemaValidator() {
    }

    public static void validate(Response response, String schema) {

        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(schema));
    }
}