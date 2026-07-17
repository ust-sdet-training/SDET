package com.capstone.support;

import com.capstone.api.*;
import com.capstone.api.specs.RequestSpecs;
import com.capstone.api.specs.ResponseSpecs;
import com.capstone.config.AppConfig;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;

public class BaseApiTest {
    protected RequestSpecification request;
    protected AuthClient authClient;

    @BeforeEach
    void setup() {
        request = RequestSpecs.requestSpec();
        authClient = new AuthClient(request);
    }

    protected String loginAs() {
        return loginAs("traveller");
    }

    protected String loginAs(String user) {
        return authClient.login(
                        AppConfig.get(user + ".email"),
                        AppConfig.get(user + ".password"))
                .then()
                .spec(ResponseSpecs.ok200())
                .extract()
                .path("token");
    }
}
