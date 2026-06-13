package ust.sdet.spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.oauth2;



public class Headers {

    static final String BASE_URL=System.getProperty("baseURL",
            System.getenv().getOrDefault("BASE_URL", "http://localhost:4000"));


    static RequestSpecification authedReqSpec(String token){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api/secure/orders")
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setAuth(oauth2(token))
                .build();
    }
}
