package com.shopkart.api;

import com.shopkart.Config.RequestSpecFactory;
import com.shopkart.data.secrets.Secrets;
import io.restassured.http.ContentType;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BobToken {


    public static String getToken() {

        Map<String, String> body = Map.of(
                "email", Secrets.get("bob.email"),
                "password", Secrets.get("bob.password")
        );

        return given()
                .spec(RequestSpecFactory.requestSpec())
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/auth/login")
                .then()
                .extract()
                .path("token");
    }
}
