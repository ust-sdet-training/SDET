package com.example.gama.Specfactory;

import com.example.gama.config.TestEnvironment;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestSpec {


    private RequestSpec() {
    }



    private static final String BASE_GET_URL = TestEnvironment.required("BASE_URL");




    public static RequestSpecification request(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_GET_URL)
                .setContentType(ContentType.JSON)
                .setBasePath("/posts")
                .build();
    }




    public static RequestSpecification requestwithparam(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_GET_URL)
                .addQueryParam("postId",1)
                .setContentType(ContentType.JSON)
                .setBasePath("/posts/1/comments?postId={postId}")
                .build();
    }




    }








