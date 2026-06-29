package com.ust.sdet.specfactory;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RequestSpec {
    private static final String BASE_URL = System.getProperty(
            "baseUrl",
            System.getenv().getOrDefault("BASE_URL", "https://restful-booker.herokuapp.com")
    );

    public static String fetchToken(String name, String pass) {
        return given().baseUri(BASE_URL)
                .auth().preemptive().basic(name, pass)
                .formParam("grant_type", "client_credentials")
                .body("""
                        {"username": "admin",
                               "password": "password123"};
                        """)
                .when()
                .post("/auth")
                .then()
                .extract().path("token");
    }

    public static RequestSpecification post(String token){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/booking")
                .setContentType(ContentType.JSON)
                .build();
    }
}
