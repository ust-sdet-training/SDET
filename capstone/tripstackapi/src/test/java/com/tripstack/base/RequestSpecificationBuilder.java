package com.tripstack.base;

import com.tripstack.config.ConfigReader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecificationBuilder {

    private RequestSpecificationBuilder() {
    }

    public static RequestSpecification requestSpecification() {

        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getBaseUrl())
                .setContentType(ContentType.JSON)
//                .addFilter(new AllureRestAssured())
                .build();
    }

    public static RequestSpecification authorizedRequest(String token) {

        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
//                .addFilter(new AllureRestAssured())
                .build();
    }
}