package com.example.gama.support;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;

public class RequestFactory {


    private static final String BASE_URL = System.getProperty(
            "baseUrl",
            System.getenv().getOrDefault("BASE_URL", "https://petstore.swagger.io/")
    );


    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";
    }

    public static RequestSpecification getapi(String key){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("v2/swagger.json")
                .setContentType(ContentType.JSON)
                .addHeader("x-api-key",key)
                .build();

    }




}
