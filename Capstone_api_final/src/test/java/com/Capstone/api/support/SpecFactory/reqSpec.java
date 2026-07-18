package com.Capstone.api.support.SpecFactory;

import com.Capstone.api.config.Config;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class reqSpec {

    public static RequestSpecification Defrequest()
    {
        return new RequestSpecBuilder()
                .setBaseUri(Config.BASE_URL)
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }
    public static RequestSpecification authRequest(String token)
    {
        return new RequestSpecBuilder()
                .addRequestSpecification(Defrequest())
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
