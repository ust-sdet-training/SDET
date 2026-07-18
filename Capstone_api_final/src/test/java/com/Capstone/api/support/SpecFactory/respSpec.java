package com.Capstone.api.support.SpecFactory;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class respSpec {
    public static ResponseSpecification okSuccess()
    {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
    public static ResponseSpecification createdSuccess() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification unauthorized()
    {
        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectContentType(ContentType.JSON)
                .build();
    }

    }
