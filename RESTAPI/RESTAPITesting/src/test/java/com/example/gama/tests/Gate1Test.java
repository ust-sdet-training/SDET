package com.example.gama.tests;

import com.example.gama.support.RequestFactory;
import com.example.gama.support.ResponseSpecFactory;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class Gate1Test {


    private static final  String API_KEY = "special-key";

    private static RequestSpecification request;


    @BeforeAll
    static void setup(){
        request = RequestFactory.getapi(API_KEY);
    }

    @Test
    @DisplayName("Verifying Persisted Order")
    void validatingflow() {
        given().spec(request).log().all()
                .when().get("").then().log().all().statusCode(200);



    }
}
