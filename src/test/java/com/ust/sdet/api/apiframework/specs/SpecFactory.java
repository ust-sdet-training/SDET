package com.ust.sdet.api.apiframework.specs;

import com.ust.sdet.api.apiframework.config.ApiConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.oauth2;

public class SpecFactory {

    private static final String BASE_URL = ApiConfig.baseUrl();

    public static RequestSpecification secureOrders(String token) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api/secure/orders")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setAuth(oauth2(token))
                .build();
    }

    public static RequestSpecification secureOrdersNoAuth() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api/secure/orders")
                .setAccept(ContentType.JSON)
                .build();
    }

    public static RequestSpecification partnerOrders(String apiKey) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api/partner/orders")
                .setAccept(ContentType.JSON)
                .addHeader("X-API-Key", apiKey)
                .build();
    }

    public static RequestSpecification jsonSpec(String basePath) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath(basePath)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification authSpec(String basePath, String token) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath(basePath)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
