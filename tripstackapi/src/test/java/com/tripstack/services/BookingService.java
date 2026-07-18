package com.tripstack.services;

import com.tripstack.base.RequestSpecificationBuilder;
import com.tripstack.constants.ApiEndpoints;
import com.tripstack.model.request.BookingRequest;
import com.tripstack.model.response.BookingResponse;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BookingService {

    public BookingResponse createBooking(String token,
                                         BookingRequest request) {

        return given()
                .spec(RequestSpecificationBuilder.authorizedRequest(token))
                .body(request)
                .log().all()
                .when()
                .post(ApiEndpoints.BOOKINGS)
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(BookingResponse.class);
    }

    public Response payBooking(String token, String bookingId) {

        return given()
                .spec(RequestSpecificationBuilder.authorizedRequest(token))
                .pathParam("id", bookingId)
                .body("{}")
                .when()
                .post(ApiEndpoints.PAY_BOOKING);
    }

    public BookingResponse confirmBooking(String token,
                                          String bookingId) {

        return given()
                .spec(RequestSpecificationBuilder.authorizedRequest(token))
                .pathParam("id", bookingId)
                .when()
                .post(ApiEndpoints.CONFIRM_BOOKING)
                .then()
                .statusCode(200)
                .extract()
                .as(BookingResponse.class);
    }

    public BookingResponse cancelBooking(String token,
                                         String bookingId) {

        return given()
                .spec(RequestSpecificationBuilder.authorizedRequest(token))
                .pathParam("id", bookingId)
                .when()
                .post(ApiEndpoints.CANCEL_BOOKING)
                .then()
                .statusCode(200)
                .extract()
                .as(BookingResponse.class);
    }

    public List<BookingResponse> getBookings(String token) {

        return given()
                .spec(RequestSpecificationBuilder.authorizedRequest(token))
                .when()
                .get(ApiEndpoints.BOOKINGS)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", BookingResponse.class);
    }

    public BookingResponse getBookingByPnr(String token,
                                           String pnr) {

        return given()
                .spec(RequestSpecificationBuilder.authorizedRequest(token))
                .pathParam("pnr", pnr)
                .when()
                .get(ApiEndpoints.BOOKING_BY_PNR)
                .then()
                .statusCode(200)
                .extract()
                .as(BookingResponse.class);
    }

    public Response resetBookings(String token) {

        return given()
                .spec(RequestSpecificationBuilder.authorizedRequest(token))
                .when()
                .post(ApiEndpoints.RESET);
    }

}