package com.api.clients;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BookingClient {

    public Response createBooking(String token,
                                  String journeyType,
                                  String inventoryId,
                                  List<String> seatIds,
                                  boolean refundable,
                                  int holdTtlSec) {

        Map<String, Object> body = Map.of(
                "journeyType", journeyType,
                "inventoryId", inventoryId,
                "seatIds", seatIds,
                "refundable", refundable,
                "holdTtlSec", holdTtlSec
        );

        return given()
                .spec(ApiSpec.requestspec())
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post("/bookings");
    }

    public Response payBooking(String token, String bookingId) {

        return given()
                .spec(ApiSpec.requestspec())
                .header("Authorization", "Bearer " + token)
                .body("{}")
                .when()
                .post("/bookings/" + bookingId + "/pay");
    }

    public Response confirmBooking(String token, String bookingId) {

        return given()
                .spec(ApiSpec.requestspec())
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/bookings/" + bookingId + "/confirm");
    }

    public Response cancelBooking(String token, String bookingId) {

        return given()
                .spec(ApiSpec.requestspec())
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/bookings/" + bookingId + "/cancel");
    }

    public Response getBookings(String token) {

        return given()
                .spec(ApiSpec.requestspec())
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/bookings");
    }

    public Response getBookingByPnr(String token, String pnr) {

        return given()
                .spec(ApiSpec.requestspec())
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/bookings/" + pnr);
    }
}