package com.travel.clients;

import com.travel.specs.RequestSpec;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public abstract class BaseAPIClient {

    protected Response get(String endpoint) {
        return given().spec(RequestSpec.publicRequest()).when().get(endpoint);
    }

    protected Response get(String endpoint, String token) {
        return given().spec(RequestSpec.authRequest(token)).when().get(endpoint);
    }

    protected Response post(String endpoint, Object body) {
        return given().spec(RequestSpec.publicRequest()).body(body).when().post(endpoint);
    }

    protected Response post(String endpoint, Object body, String token) {
        return given().spec(RequestSpec.authRequest(token)).body(body).when().post(endpoint);
    }

    protected Response post(String endpoint, String token) {
        return given().spec(RequestSpec.authRequest(token)).when().post(endpoint);
    }
}