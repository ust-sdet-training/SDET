package com.ust.sdet.api.specs;

import com.ust.sdet.api.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {

    public RequestSpecification build(ConfigManager configManager) {
        return new RequestSpecBuilder()
                .setBaseUri(configManager.getBaseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public RequestSpecification buildWithAuth(ConfigManager configManager, String token) {
        RequestSpecification requestSpecification = build(configManager);
        requestSpecification.header("Authorization", "Bearer " + token);
        return requestSpecification;
    }
}
