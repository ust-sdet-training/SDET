package com.tripstack.tests;

import com.tripstack.config.BaseConfig;
import com.tripstack.constants.EndPoints;
import com.tripstack.utils.TokenManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class SecurityTests {

    static String token;

    @BeforeAll
    static void setup() {

        BaseConfig.setup();
        token = TokenManager.getToken();

    }

    @Test
    void invalidToken() {
        given()
                .header("Authorization", "Bearer invalidToken")
                .when()
                .get(EndPoints.BOOKINGS)
                .then()
                .statusCode(401);
    }

    @Test
    void tamperedToken() {
        given()
                .header("Authorization", "Bearer " + token + "abc")
                .when()
                .get(EndPoints.BOOKINGS)
                .then()
                .statusCode(401);
    }

//    @Test
//    void cancelAnotherEmployeeBooking() {
//
//        String anotherBookingId = "007f9a85-04b2-4587-8dd8-52145617bd78";
//
//        given()
//                .header("Authorization", "Bearer " + token)
//                .contentType("application/json")
//                .body("{}")
//                .when()
//                .post(EndPoints.BOOKINGS + "/" + anotherBookingId + "/cancel")
//                .then()
//                .statusCode(403);
//    }
}