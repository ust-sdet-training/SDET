package com.routepulse.api.client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class HttpGateway {
    public Response get(String path, RequestSpecification spec) {
        return given().spec(spec).get(path);
    }

    public Response post(String path, Object body, RequestSpecification spec) {
        if (body == null) {
            return given().spec(spec).post(path);
        }
        return given().spec(spec).body(body).post(path);
    }
}
