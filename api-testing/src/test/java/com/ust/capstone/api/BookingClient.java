package com.ust.capstone.api;

import io.restassured.response.Response;

import static com.ust.capstone.api.ApiSpec.authedSpec;
import static io.restassured.RestAssured.given;

public class BookingClient {

    public Response getBookings(String token) {

        return given()
                .spec(authedSpec(token))
                .when()
                .get("/bookings");
    }

    public Response createBooking(String token, String body) {
        return given()
                .spec(authedSpec(token))
                .body(body)
                .post("/bookings");
    }

    public Response payBooking(String token, String bookingId) {
        return given()
                .spec(authedSpec(token))
                .body("{}")
                .post("/bookings/" + bookingId + "/pay");
    }

    public Response confirmBooking(String token, String bookingId) {
        return given()
                .spec(authedSpec(token))
                .post("/bookings/" + bookingId + "/confirm");
    }

    public Response cancelBooking(String token, String bookingId) {
        return given()
                .spec(authedSpec(token))
                .post("/bookings/" + bookingId + "/cancel");
    }
}
