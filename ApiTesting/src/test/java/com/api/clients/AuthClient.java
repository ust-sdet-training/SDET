package com.api.clients;

import com.api.data.Secrets;
import com.api.data.TestData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthClient {
    public Response loginSpec(Map<String,String> body){
        return given()
                .spec(ApiSpec.requestspec()).basePath("/auth/login")
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .body(body)
                .when().post("");
    }

    public Response loginAsDave(){
        Map<String, String> body = Map.of(
                "email", Secrets.get("HEIDI_EMAIL"),
                "password",Secrets.get("HEIDI_PASSWORD")
        );
        return loginSpec(body);
    }

    public Response adminPing(String token) {

        return given()
                .spec(ApiSpec.requestspec())
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/auth/admin-ping");
    }


}
