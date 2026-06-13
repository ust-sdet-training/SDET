package com.ust.sdet.api.specificationfactory;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class SpecFactory {

    public static final String BASE_URL = System.getProperty("baseurl",System.getenv().getOrDefault("BASEURL","http://localhost:4000"));

    public static Map<String,String> generateTokenBody =
            Map.of(System.getenv("GRANT_TYPE"),System.getenv("GRANT_SECRET"));

    public static String getToken(String username,String password)
    {
        return   given()
                .spec(oauthTokenGenerateSpecRequest)
                .auth()
                .preemptive()
                .basic(username ,password)
                .when()
                .post("/oauth/token")
                .then()
                .spec(oauthTokenGenerateSpecResponse)
                .extract()
                .path("access_token");

    }

    public static RequestSpecification authedOrderCreatedRequest =
            new RequestSpecBuilder()
                    .setBaseUri(BASE_URL)
                    .setBasePath("/api/secure/orders")
                    .addHeader("Content-Type", "application/json")
                    .build();


    public static RequestSpecification commonJsonRequest =
            new RequestSpecBuilder()
                    .setBaseUri(BASE_URL)
                    .setBasePath("/api")
                    .addHeader("Content-Type","application/json")
                    .build();


    public static ResponseSpecification commonJsonFetchResponse =
            new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .expectStatusCode(200)
                    .build();

    public static ResponseSpecification commonJsonConflictResponse =
            new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .expectStatusCode(409)
                    .build();

    public static ResponseSpecification commonJsonForbiddenResponse =
            new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .expectStatusCode(403)
                    .build();

    public static ResponseSpecification commonJsonUnAuthoriseResponse =
            new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .expectStatusCode(401)
                    .build();




    public static ResponseSpecification commonJsonCreateResponse =
            new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .expectStatusCode(201)
                    .build();

    public static ResponseSpecification commonJsonDeleteResponse =
            new ResponseSpecBuilder()
                    .expectStatusCode(204)
                    .build();

    public static ResponseSpecification commonJsonNotFountResponse =
            new ResponseSpecBuilder()
                    .expectStatusCode(404)
                    .build();

    //token

    public static RequestSpecification oauthTokenGenerateSpecRequest= new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .setBasePath("/api")
            .setContentType("application/x-www-form-urlencoded; charset=UTF-8")
            .addFormParam("grant_type",generateTokenBody.get("grant_type"))
            .build();


    public static ResponseSpecification oauthTokenGenerateSpecResponse=
            new ResponseSpecBuilder()
                    .expectStatusCode(200)
                    .expectContentType(ContentType.JSON)
                    .build();



}
