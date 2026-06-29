package com.ust.sdet.api.support;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.ust.sdet.api.config.ApiConfig.BASE_URL;

public class SpecFactory {
    public static RequestSpecification jsonRequest =
            new RequestSpecBuilder()
                    .setBaseUri(BASE_URL)
                    .setBasePath("")
                    .addHeader("Content-Type","application/json")
                    .build();


    public static ResponseSpecification jsonCreateResponse =
            new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .expectStatusCode(201)
                    .build();


}
