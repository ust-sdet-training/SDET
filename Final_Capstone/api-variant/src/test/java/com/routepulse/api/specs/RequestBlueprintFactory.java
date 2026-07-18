package com.routepulse.api.specs;

import com.routepulse.api.config.TravelConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestBlueprintFactory {
    public RequestSpecification build(TravelConfigManager configManager) {
        return new RequestSpecBuilder()
                .setBaseUri(configManager.getBaseUrl())
                .setContentType(ContentType.JSON)
                .build();
    }

    public RequestSpecification buildWithAuth(TravelConfigManager configManager, String token) {
        return new RequestSpecBuilder()
                .setBaseUri(configManager.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
