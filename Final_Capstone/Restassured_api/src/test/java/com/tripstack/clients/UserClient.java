package com.tripstack.clients;


import static io.restassured.RestAssured.*;


import com.tripstack.config.Config;
import com.tripstack.utils.TokenManager;



public class UserClient {



    public void getMe(){


        given()

                .baseUri(Config.get("base.url"))

                .header(
                        "Authorization",
                        "Bearer "+TokenManager.getToken()
                )

                .when()

                .get("/api/auth/me")

                .then()

                .statusCode(200);



    }



}