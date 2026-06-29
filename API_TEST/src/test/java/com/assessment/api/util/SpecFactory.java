package com.assessment.api.util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class SpecFactory {

    public static RequestSpecification reqSpec() {

        return new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setContentType("application/json")
                .build();
    }

    public static String postURL() {
        return "/posts/1";
    }
}