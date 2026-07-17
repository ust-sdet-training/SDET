package com.apitesting.client;

import com.apitesting.support.builders.ApiSpecBuilders;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookingClient {

    public Response createBooking(String token, Object bookingRequest) {

        return given()
                .spec(ApiSpecBuilders.authSpec(token))
                .body(bookingRequest)
                .when()
                .post("/bookings");
    }

    public Response pay(String token, String bookingId) {
        return given()
                .spec(ApiSpecBuilders.authSpec(token))
                .body("{}")
                .when()
                .post("/bookings/" + bookingId + "/pay");
    }

    public Response confirm(String token, String bookingId) {
        return given()
                .spec(ApiSpecBuilders.authSpec(token))
                .when()
                .post("/bookings/" + bookingId + "/confirm");
    }



    public Response getBooking(String token, String pnr)
    {
        return given()
                .spec(ApiSpecBuilders.authSpec(token))
                .when()
                .get("/bookings/" + pnr);
    }

//    public Response getBookings(
//            String token
//    ) {
//
//        return given()
//                .spec(ApiSpecBuilders.authSpec(token))
//                .when()
//                .get("/bookings");
//    }
}