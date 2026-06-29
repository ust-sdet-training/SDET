package com.ust.sdet.specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

public class CommonSpec {
    static final String BASE_URL = "https://restful-booker.herokuapp.com";

    public static String token() {
        return given()
                .spec(commonReqSpec())
                .body(Map.of(
                        "username", "admin",
                        "password", "password123"))
                .when().post("/auth")
                .then()
                .spec(commonResSpec())
                .body("token", notNullValue())
                .extract().jsonPath().getString("token");
    }

    public static RequestSpecification authReqSpec(String token){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/booking")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("Cookie", "token=" + token)
                .build();
    }


    public static RequestSpecification commonReqSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification commonResSpec() {
        return  new ResponseSpecBuilder()
//                .expectStatusCode(200)
//                .expectContentType(ContentType.JSON)
//                .expectResponseTime(lessThan(800L))
                .build();
    }
}
