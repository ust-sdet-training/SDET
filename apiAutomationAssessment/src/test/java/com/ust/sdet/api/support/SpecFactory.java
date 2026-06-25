package com.ust.sdet.api.support;

import com.ust.sdet.api.config.TestEnvironment;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;
import static com.ust.sdet.api.config.ApiConfig.BASE_URL;
import static io.restassured.RestAssured.given;

public class SpecFactory {


    public static RequestSpecification authedTokenCreatedRequest =
            new RequestSpecBuilder()
                    .setBaseUri(BASE_URL)
                    .setBasePath("")
                    .addHeader("Content-Type", "application/json")
                    .build();


    public static RequestSpecification commonJsonRequest =
            new RequestSpecBuilder()
                    .setBaseUri(BASE_URL)
                    .setBasePath("")
                    .addHeader("Content-Type","application/json")
                    .build();


    public static ResponseSpecification commonJsonResponse =
            new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .expectStatusCode(200)
                    .build();


    //token

    public static RequestSpecification oauthTokenGenerateSpecRequest= new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setBasePath("")
            .setContentType("application/x-www-form-urlencoded; charset=UTF-8")
//            .addFormParam("grant_type",generateTokenBody.get("grant_type"))
            .build();

}
