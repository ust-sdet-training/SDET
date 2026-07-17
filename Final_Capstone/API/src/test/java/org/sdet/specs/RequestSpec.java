package org.sdet.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.sdet.config.Secrets;

import static io.restassured.http.ContentType.JSON;

public class RequestSpec {

    public static RequestSpecification requestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri(Secrets.baseUrl())
                .setContentType(JSON)
                .setAccept(JSON)
                .build();
    }

}