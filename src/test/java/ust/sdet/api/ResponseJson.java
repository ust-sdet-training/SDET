package ust.sdet.api;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class ResponseJson {
    public static ResponseSpecification okJson(){
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
    public static ResponseSpecification okCreatedJson(){
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();
    }
    public static ResponseSpecification ErrorJson(int errorCode){
        return new ResponseSpecBuilder()
                .expectStatusCode(errorCode)
                .expectContentType(ContentType.JSON)
                .build();
    }
}
