package org.sdet.API_Framework.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.sdet.API_Framework.config.ConfigManager;
import org.sdet.API_Framework.utils.TokenManager;

public final class RequestSpec {

    private RequestSpec() {}


    public static RequestSpecification withoutToken() {

        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getBaseUrl())
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification withToken() {

        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization",
                        "Bearer " + TokenManager.getToken())
                .build();
    }

}