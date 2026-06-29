package com.ust.sdet.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Spec {

    private static final String BASE_URL = "";

    public static RequestSpecification setup(){
        return new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com/booking")
                .setBasePath("/")
                .setContentType(ContentType.JSON)
                .build();
    }
}
