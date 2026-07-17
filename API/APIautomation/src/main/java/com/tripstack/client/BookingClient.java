package com.tripstack.client;

import com.tripstack.config.ConfigManager;
import com.tripstack.models.BookingRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookingClient {

    @Step("POST /bookings (hold seats)")
    public Response createBooking(String token, BookingRequest request) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("/bookings");
    }

    @Step("POST /bookings/{bookingId}/pay")
    public Response pay(String token, String bookingId) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{}")
                .when()
                .post("/bookings/" + bookingId + "/pay");
    }

    @Step("POST /bookings/{bookingId}/confirm")
    public Response confirm(String token, String bookingId) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{}")
                .when()
                .post("/bookings/" + bookingId + "/confirm");
    }

    @Step("POST /bookings/{bookingId}/cancel")
    public Response cancel(String token, String bookingId) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{}")
                .when()
                .post("/bookings/" + bookingId + "/cancel");
    }

    @Step("GET /bookings (list my namespace)")
    public Response listMyBookings(String token) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/bookings");
    }

    @Step("GET /bookings/{pnr}")
    public Response getByPnr(String token, String pnr) {
        return given()
                .baseUri(ConfigManager.BASE_URL)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/bookings/" + pnr);
    }
}