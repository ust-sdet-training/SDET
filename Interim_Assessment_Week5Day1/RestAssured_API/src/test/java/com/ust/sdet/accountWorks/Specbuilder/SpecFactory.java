package com.ust.sdet.accountWorks.Specbuilder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.ust.sdet.accountWorks.utils.EnvironmentCheck.*;

public class SpecFactory {

    public static RequestSpecification getReqSpecToPosts1(String path){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath(path)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();
    }

}
