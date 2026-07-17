package com.tripstack.clients;

import static io.restassured.RestAssured.*;

import com.tripstack.config.Config;


public class AuthClient {


    public String login(){


        return given()

                .baseUri(Config.get("base.url"))

                .contentType("application/json")

                .body("{\n" +
                        "\"email\":\"trent@tripstack.test\",\n" +
                        "\"password\":\"Password@123\"\n" +
                        "}")

                .when()

                .post("/api/auth/login")

                .then()

                .statusCode(200)

                .extract()

                .path("token");

    }

}