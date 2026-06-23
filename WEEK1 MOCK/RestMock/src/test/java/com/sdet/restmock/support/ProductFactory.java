package com.sdet.restmock.support;

import com.sdet.restmock.config.BaseConfig;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.shaded.org.checkerframework.checker.index.qual.LessThan;
import com.sdet.restmock.config.BaseConfig.*;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.Matchers.lessThan;
public class ProductFactory {

        static final String apikey=BaseConfig.api_key;
        static final String baseURL=BaseConfig.BASE_URL;
   public  static  ResponseSpecification ok=new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build();
    public static ResponseSpecification created()
    {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(300L))
                .build();


    }

    public static RequestSpecification get=new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .setBasePath("/store/inventory")
            .setContentType("application/json")
            .setAccept("application/json")
            .build();

    public static RequestSpecification p  =new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .setBasePath("/store/order")
            .setContentType("application/json")
            .setAccept("application/json")
            .build();



}
