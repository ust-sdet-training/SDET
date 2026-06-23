package com.ust.sdet.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.net.URI;

import static org.hamcrest.Matchers.lessThan;

public class CommonSpecs {

    private static final URI BASE_URL = URI.create("https://petstore.swagger.io/");

    public static RequestSpecification commonReqSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/v2")
                .setAccept(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification commonResSpec() {
        return  new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
}
