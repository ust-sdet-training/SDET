package com.gamagate1.support;

import com.gamagate1.config.ApiConfig;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {
    public static RequestSpecification auth = new RequestSpecBuilder()
                                        .setBasePath("v2/")
                                        .setAccept(ContentType.JSON)
                                        .setContentType(ContentType.JSON)
                                        .addHeader("x-api-key", ApiConfig.API_KEY)
                                        .setBaseUri(ApiConfig.BASE_URL)
                                        .build();
    
}
