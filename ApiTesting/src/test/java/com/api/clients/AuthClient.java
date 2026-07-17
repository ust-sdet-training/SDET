package com.api.clients;

import com.api.data.Secrets;
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
                "email", Secrets.get("DAVE_EMAIL"),
                "password", Secrets.get("DAVE_PASSWORD")
        );
        return loginSpec(body);
    }

    public Response loginAsAdmin() {
        Map<String, String> body = Map.of(

                "email", Secrets.get("ADMIN_EMAIL"),
                "password", Secrets.get("ADMIN_PASSWORD")
        );
        return loginSpec(body);

    }
}
