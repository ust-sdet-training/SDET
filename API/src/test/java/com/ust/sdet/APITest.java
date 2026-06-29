package com.ust.sdet;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

public class APITest {
    @Test
    void updateBooking() {
        given()
            .baseUri("https://restful-booker.herokuapp.com")
            .contentType(ContentType.JSON)
            .body(Map.of("username", "admin", "password", "password123"))
        .when()
            .post("/auth")
        .then()
            .statusCode(200)
            .extract().path("token");
        
        given()
            .baseUri("https://restful-booker.herokuapp.com")
            .contentType(ContentType.JSON)
            .body(Map.of(
                    "firstname", "Jim",
                    "lastname", "Brown",
                    "totalprice", 111,
                    "depositpaid", true,
                    "bookingdates", Map.of("checkin", "2018-01-01",
                                            "checkout", "2019-01-01"),
                    "additionalneeds" ,"Breakfast"))
        .when()
            .post("booking")
        .then()
            .statusCode(200)
            .extract().path("bookingid");
    }
}
