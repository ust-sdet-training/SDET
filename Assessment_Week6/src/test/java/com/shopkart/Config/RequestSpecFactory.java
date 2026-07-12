package com.shopkart.Config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {



        public static RequestSpecification requestSpec() {

            return new RequestSpecBuilder()
                    .setBaseUri(ApiConfig.apply())
                    .setContentType(ContentType.JSON)
                    .build();
        }

}
