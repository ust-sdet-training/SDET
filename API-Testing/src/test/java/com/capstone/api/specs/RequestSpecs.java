package com.capstone.api.specs;

import com.capstone.config.AppConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {
    private RequestSpecs() {}
    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(AppConfig.get("base.url"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setBasePath("")
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification authorizedSpec(String token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(requestSpec())
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
