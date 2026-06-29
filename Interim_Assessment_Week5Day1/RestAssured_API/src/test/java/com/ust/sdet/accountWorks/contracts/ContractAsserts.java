package com.ust.sdet.accountWorks.contracts;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;


public class ContractAsserts {
    private ContractAsserts(){
    }

    public static void assertOkJsonContract(Response resp, String schemaPath){
        resp.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath(schemaPath));
    }
}
