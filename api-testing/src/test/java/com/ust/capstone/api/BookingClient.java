package com.ust.capstone.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookingClient {

    public Response getBookings(String token) {

        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/bookings");
    }

}
