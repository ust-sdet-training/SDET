package com.apitest.support;

import com.apitest.config.ApiConfig;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public class RequestSpec {
    public static RequestSpecification requestSpec = new RequestSpecBuilder()
                                        .setAccept(ContentType.JSON)
                                        .setContentType(ContentType.JSON)
                                        .setBaseUri(ApiConfig.BASE_URL)
                                        .setAccept(ContentType.JSON)
                                        .build();

}
