package com.apitesting.api;

import com.apitesting.data.model.ApiModels.Booking;
import com.apitesting.support.specifications.RequestSpecifications;
import com.apitesting.support.specifications.ResponseSpecifications;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class BookingApi {
    public Booking holdBusSeat(String token, String busId, String seatId) {
        return given().spec(RequestSpecifications.authenticatedRequest(token))
                .body(Map.of("journeyType", "bus", "inventoryId", busId, "seatIds", List.of(seatId)))
                .when().post("/bookings").then().spec(ResponseSpecifications.created()).extract().as(Booking.class);
    }

    public Booking pay(String token, String bookingId) {
        return given().spec(RequestSpecifications.authenticatedRequest(token))
                .body(Map.of("method", "card", "cardToken", "tok_test_visa"))
                .when().post("/bookings/{id}/pay", bookingId).then().spec(ResponseSpecifications.ok()).extract().as(Booking.class);
    }

    public Booking confirm(String token, String bookingId) {
        return given().spec(RequestSpecifications.authenticatedRequest(token)).body(Map.of())
                .when().post("/bookings/{id}/confirm", bookingId).then().spec(ResponseSpecifications.ok()).extract().as(Booking.class);
    }

    public Booking[] list(String token) {
        return given().spec(RequestSpecifications.authenticatedRequest(token)).when().get("/bookings")
                .then().spec(ResponseSpecifications.ok()).extract().as(Booking[].class);
    }

    public Booking getByPnr(String token, String pnr) {
        return given().spec(RequestSpecifications.authenticatedRequest(token)).when().get("/bookings/{pnr}", pnr)
                .then().spec(ResponseSpecifications.ok()).extract().as(Booking.class);
    }

    public void cancel(String token, String bookingId) {
        given().spec(RequestSpecifications.authenticatedRequest(token))
                .when().post("/bookings/{id}/cancel", bookingId)
                .then().spec(ResponseSpecifications.ok());
    }

    public void assertCannotCancel(String token, String bookingId) {
        given().spec(RequestSpecifications.authenticatedRequest(token))
                .when().post("/bookings/{id}/cancel", bookingId)
                .then().statusCode(403);
    }

    public void assertUnauthorized(String token) {
        given().spec(RequestSpecifications.authenticatedRequest(token)).when().get("/bookings")
                .then().spec(ResponseSpecifications.unauthorized()).body("error", equalTo("unauthorized"));
    }
}
