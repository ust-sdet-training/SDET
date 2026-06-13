package com.ust.sdet.api.specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.ust.sdet.api.support.Utils.*;
import static io.restassured.RestAssured.preemptive;

public class LoginSpec extends CommonSpec{
    public static RequestSpecification oauthReqSpec(String id, String secret){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api")
                .setContentType("application/x-www-form-urlencoded; charset=UTF-8")
                .setAccept(ContentType.JSON)
                .addFormParam("grant_type", "client_credentials")
                .setAuth(preemptive().basic(id, secret))
                .build();
    }
    public static RequestSpecification apiKeyReqSpec(String path) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api"+ path)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("X-API-Key", API_KEY)
                .build();
    }
}
