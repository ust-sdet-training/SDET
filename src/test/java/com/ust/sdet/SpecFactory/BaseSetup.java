package com.ust.sdet.SpecFactory;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;

public class BaseSetup {

    private static final String BASE_URL = "";

    public static RequestSpecification setup(){
        return new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setBasePath("/")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();
    }
    public static ResponseSpecification jSONResponseSpec(int code){
        return new ResponseSpecBuilder()
                .expectStatusCode(code)
                .expectContentType(ContentType.JSON)
                .build();
    }
}
