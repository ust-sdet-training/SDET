package com.capstone.api;

import com.capstone.api.specs.RequestSpecs;
import com.capstone.api.specs.ResponseSpecs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BookingApiClient {
    private final RequestSpecification request;

    public BookingApiClient(RequestSpecification request) {
        this.request = request;
    }

//    public Response resetNamespace(String token) {
//        return given(request)
//                .spec(com.capstone.api.specs.RequestSpecs.authorizedSpec(token))
//                .when()
//                .post("/reset");
//    }

    public Response holdBooking(String token, String journeyType, String inventoryId, List<String> seatIds) {
        return given(request)
                .spec(RequestSpecs.authorizedSpec(token))
                .body(Map.of(
                        "journeyType", journeyType,
                        "inventoryId", inventoryId,
                        "seatIds", seatIds
                ))
                .when()
                .post("/bookings");
    }

    public Response holdAvailableSeat(String token, String flightId, List<String> seatIds) {
        for (String seatId : seatIds) {
            Response response = holdBooking(token, "flight", flightId, List.of(seatId));
            if (response.statusCode() == 201) {
                return response;
            }
        }
        throw new RuntimeException("No available seat could be booked.");
    }

    public Response payBooking(String token, String bookingId) {
        return given(request)
                .spec(RequestSpecs.authorizedSpec(token))
                .pathParam("id", bookingId)
                .when()
                .post("/bookings/{id}/pay");
    }

    public Response confirmBooking(String token, String bookingId) {
        return given(request)
                .spec(RequestSpecs.authorizedSpec(token))
                .pathParam("id", bookingId)
                .when()
                .post("/bookings/{id}/confirm");
    }

    public Response getBookings(String token) {
        return given(request)
                .spec(RequestSpecs.authorizedSpec(token))
                .when()
                .get("/bookings");
    }

    public Response getBookingByPnr(String token, String pnr) {
        return given(request)
                .spec(RequestSpecs.authorizedSpec(token))
                .pathParam("pnr", pnr)
                .when()
                .get("/bookings/{pnr}");
    }
}
