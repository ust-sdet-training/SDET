package com.week7.finalgate.API.Factory;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public final class RequestFactory {

    private RequestFactory() {}

    public static RequestSpecification publicRequest() {

        return RestAssured
                .given()
                .contentType("application/json")
                .accept("application/json");

    }

    public static RequestSpecification authorizedRequest(String token) {

        return RestAssured
                .given()
                .contentType("application/json")
                .accept("application/json")
                .header("Authorization",
                        "Bearer " + token);

    }

}
