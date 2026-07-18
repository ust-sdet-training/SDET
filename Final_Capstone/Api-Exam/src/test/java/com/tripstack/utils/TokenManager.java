package com.tripstack.utils;

import com.tripstack.constants.EndPoints;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class TokenManager {

    private static String token;

    public static String getToken() {

        if(token==null){

            token = given()
                    .contentType("application/json")
                    .body(Map.of(
                            "email", ConfigReader.get("email"),
                            "password", ConfigReader.get("password")
                    ))
                    .post(EndPoints.LOGIN)
                    .jsonPath()
                    .getString("token");
        }

        return token;
    }
}