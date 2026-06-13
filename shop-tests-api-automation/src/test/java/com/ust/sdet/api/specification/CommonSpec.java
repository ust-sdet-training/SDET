package com.ust.sdet.api.specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.ust.sdet.api.support.Utils.BASE_URL;
import static org.hamcrest.Matchers.lessThan;

public class CommonSpec {
    static RequestSpecification commonReqSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api")
                .build();
    }

    public static ResponseSpecification commonResSpec() {
        return  new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(800L))
                .build();
    }

    public static RequestSpecification commonReqSpecXML(String path){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api" + path)
                .setContentType(ContentType.XML)
                .setAccept(ContentType.XML)
                .build();
    }
}
