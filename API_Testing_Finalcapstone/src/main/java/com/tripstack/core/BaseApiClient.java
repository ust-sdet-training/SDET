package com.tripstack.core;

import java.util.Map;
import java.util.function.Supplier;

import com.tripstack.config.RequestSpecificationBuilder;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApiClient {

    private static final int MAX_RETRIES = 3;

    protected Response get(String endpoint) {
        return getWithRetry(() -> get(endpoint, RequestSpecificationBuilder.build()));
    }

    protected Response get(String endpoint, String token) {
        return getWithRetry(() -> get(endpoint, RequestSpecificationBuilder.build(token)));
    }

    protected Response get(String endpoint, Map<String, ?> queryParams) {
        return getWithRetry(() -> given()
                .spec(RequestSpecificationBuilder.build())
                .queryParams(queryParams)
                .when()
                .get(endpoint));
    }

    protected Response get(String endpoint, String token, Map<String, ?> queryParams) {
        return getWithRetry(() -> given()
                .spec(RequestSpecificationBuilder.build(token))
                .queryParams(queryParams)
                .when()
                .get(endpoint));
    }

    protected Response post(String endpoint, Object request) {
        return postWithRetry(() -> given()
                .spec(RequestSpecificationBuilder.build())
                .body(request)
                .when()
                .post(endpoint));
    }

    protected Response post(String endpoint, Object request, String token) {
        return postWithRetry(() -> given()
                .spec(RequestSpecificationBuilder.build(token))
                .body(request)
                .when()
                .post(endpoint));
    }

    protected Response put(String endpoint, Object request, String token) {
        return postWithRetry(() -> given()
                .spec(RequestSpecificationBuilder.build(token))
                .body(request)
                .when()
                .put(endpoint));
    }

    protected Response delete(String endpoint, String token) {
        return getWithRetry(() -> given()
                .spec(RequestSpecificationBuilder.build(token))
                .when()
                .delete(endpoint));
    }

    private Response get(String endpoint, RequestSpecification specification) {
        return given().spec(specification).when().get(endpoint);
    }

    private Response getWithRetry(Supplier<Response> request) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            Response response = request.get();
            if (!shouldRetry(response) || attempt == MAX_RETRIES) {
                return response;
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                return response;
            }
        }
        return request.get();
    }

    private Response postWithRetry(Supplier<Response> request) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            Response response = request.get();
            if (!shouldRetry(response) || attempt == MAX_RETRIES) {
                return response;
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                return response;
            }
        }
        return request.get();
    }

    private boolean shouldRetry(Response response) {
        int statusCode = response.getStatusCode();
        String contentType = response.getContentType();
        return statusCode == 502 || statusCode == 503 || statusCode == 504 || (contentType != null && contentType.toLowerCase().contains("text/html"));
    }
}
