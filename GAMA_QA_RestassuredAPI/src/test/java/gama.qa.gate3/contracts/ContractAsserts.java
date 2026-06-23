package gama.qa.gate3.contracts;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ContractAsserts {
    private ContractAsserts(){
    }

    public static void assertOkJsonContract(Response resp, String schemaPath){
        resp.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath(schemaPath));
    }

    public static void assertOkXmlContract(Response resp, String schemaPath){
        resp.then()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body(matchesXsdInClasspath(schemaPath));
    }

    static void assertCreatedJsonContract(Response resp, String schemaPath){
        resp.then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath(schemaPath));

    }
}
