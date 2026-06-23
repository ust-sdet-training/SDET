package com.ust.sdet.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.util.Map.entry;

public class DeletePetByIdTest {
    @Test
    @DisplayName("Create a new entry for pets")
    void CreatenewPet(){

        Map pet_details= Map.ofEntries(
                entry("id", 1234656),
                entry("category", Map.of("id",0,"name","test")),
                entry("name", "Doggo"),
                entry("photoUrls", List.of("unknown")),
                entry("tags", List.of(Map.of("id",0,"name","test"))),
                entry("status", "available")
        );



       Response response =
               given()
                .baseUri("https://petstore.swagger.io/")
                .basePath("/v2")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(pet_details)
        .when()
                .post("/pet")
                .then()
                       .body(matchesJsonSchemaInClasspath("schemas/json/petpost.schema.json"))
                       .extract().response();

//        System.out.println(response.asPrettyString());


        Response getByStatusResponse=
                given()
                        .baseUri("https://petstore.swagger.io/")
                        .basePath("/v2")
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .body(pet_details)
                        .pathParams("id",1234656)
                        .when()
                        .delete("/pet/{id}")
                        .then()
                        .statusCode(200)
                        .extract().response();

        //System.out.println(response.asPrettyString());

        given()
                .baseUri("https://petstore.swagger.io/")
                .basePath("/v2")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(pet_details)
                .pathParams("id",1234656)
                .when()
                .get("/pet/{id}")
                .then()
                .statusCode(404);


    }

    public static void assertOkJsonContract(Response response, String schemaPath){

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath(schemaPath));

    }
}
