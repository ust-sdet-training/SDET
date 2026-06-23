package com.apimocktest.support;

import com.apimocktest.config.ApiConfig;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class requestSpecifications {
    public static RequestSpecification auth = new RequestSpecBuilder()
                                        .setBasePath("api/")
                                        .setContentType(ContentType.JSON)
                                        .addHeader("x-api-key", ApiConfig.API_KEY)
                                        .setBaseUri(ApiConfig.BASE_URL)
                                        .build();
}
