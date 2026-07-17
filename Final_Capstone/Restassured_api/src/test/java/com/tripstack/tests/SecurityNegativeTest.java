package com.tripstack.tests;


import static io.restassured.RestAssured.*;


import org.junit.jupiter.api.Test;


import com.tripstack.config.Config;



public class SecurityNegativeTest {


    @Test

    void invalidToken(){


        given()

                .baseUri(Config.get("base.url"))

                .header(
                        "Authorization",
                        "Bearer wrongtoken"
                )

                .when()

                .get("/api/bookings")

                .then()

                .statusCode(401);


    }



    @Test

    void adminPrivilegeNegative(){


        given()

                .baseUri(Config.get("base.url"))

                .header(
                        "Authorization",
                        "Bearer "+wrongToken()
                )


                .when()

                .get("/api/auth/admin-ping")

                .then()

                .statusCode(403);


    }



    private String wrongToken(){

        return "invalid";

    }


}