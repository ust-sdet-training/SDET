package com.apitesting.api;

import com.apitesting.support.specifications.RequestSpecifications;
import com.apitesting.support.specifications.ResponseSpecifications;

import static io.restassured.RestAssured.given;


public class NamespaceApi {
    public void reset(String token) {
        given().spec(RequestSpecifications.authenticatedRequest(token)).body("{}")
                .when().post("/reset").then().spec(ResponseSpecifications.ok());
    }
}
