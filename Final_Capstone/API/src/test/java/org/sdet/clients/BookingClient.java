package org.sdet.clients;

import io.restassured.response.Response;
import org.sdet.base.BaseAPI;
import org.sdet.constants.Endpoints;
import org.sdet.model.request.BookingRequest;
import org.sdet.utils.TokenManager;

import static io.restassured.RestAssured.given;

public class BookingClient extends BaseAPI {

    // Create Booking
    public Response createBooking(BookingRequest request) {

        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .body(request)

                .when()
                .post(Endpoints.BOOKINGS)

                .then()
                .extract()
                .response();
    }

    // Get All Bookings
    public Response getAllBookings() {

        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + TokenManager.getToken())

                .when()
                .get(Endpoints.BOOKINGS)

                .then()
                .extract()
                .response();
    }

    // Get Booking By PNR
    public Response getBookingByPNR(String pnr) {

        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .pathParam("pnr", pnr)

                .when()
                .get(Endpoints.BOOKING_BY_PNR)

                .then()
                .extract()
                .response();
    }

    // Confirm Booking
    public Response confirmBooking(String bookingId) {

        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .pathParam("id", bookingId)

                .when()
                .post(Endpoints.CONFIRM)

                .then()
                .extract()
                .response();
    }

    // Cancel Booking
    public Response cancelBooking(String bookingId) {

        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .pathParam("id", bookingId)

                .when()
                .post(Endpoints.CANCEL)

                .then()
                .extract()
                .response();
    }

}