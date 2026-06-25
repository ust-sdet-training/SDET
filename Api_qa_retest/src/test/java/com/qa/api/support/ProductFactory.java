package com.qa.api.support;

import com.qa.api.config.BaseConfig;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;

public class ProductFactory {

        static final String baseURL=BaseConfig.BASE_URL;
   public  static  ResponseSpecification ok=new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build();



    public static RequestSpecification post=new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .setBasePath("/auth")
            .setContentType("application/json")
            .setAccept("application/json")
            .build();
    public static RequestSpecification get=new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .setBasePath("/booking/2")
            .setContentType("application/json")
            .setAccept("application/json")
            .build();

//    public static RequestSpecification put  =new RequestSpecBuilder()
//            .setBaseUri(baseURL)
//            .setBasePath("/booking/2")
//            .setContentType("application/json")
//            .setAccept("application/json")
//            .build();


}
