package com.travelbooking.clients;

import com.travelbooking.specs.RequestSpecs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class BaseClient {

    protected RequestSpecification request() {
        return RequestSpecs.defaultSpec();
    }

    protected Response get(String endpoint) {
        return request()
                .get(endpoint);
    }

    protected Response post(String endpoint, Object body) {
        return request()
                .body(body)
                .post(endpoint);
    }

    protected Response put(String endpoint, Object body) {
        return request()
                .body(body)
                .put(endpoint);
    }

    protected Response delete(String endpoint) {
        return request()
                .delete(endpoint);
    }
}