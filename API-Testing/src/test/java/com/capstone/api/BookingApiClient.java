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

    public Response resetNamespace(String token) {
        return given(request)
                .spec(RequestSpecs.authorizedSpec(token))
                .when()
                .post("/reset");
    }

    public Response holdBooking(String token, String journeyType, String inventoryId, List<String> seatIds) {
        return holdBooking(token, journeyType, inventoryId, seatIds, null);
    }

    public Response holdBooking(String token, String journeyType, String inventoryId, List<String> seatIds,
                                Integer holdTtlSec) {
        Map<String, Object> payload = new java.util.HashMap<>();
        payload.put("journeyType", journeyType);
        payload.put("inventoryId", inventoryId);
        payload.put("seatIds", seatIds);
        if (holdTtlSec != null) {
            payload.put("holdTtlSec", holdTtlSec);
        }

        return given(request)
                .spec(RequestSpecs.authorizedSpec(token))
                .body(payload)
                .when()
                .post("/bookings");
    }

    public Response holdAvailableSeat(String token, String flightId, List<String> seatIds) {
        return holdAvailableSeat(token, flightId, seatIds, null);
    }

    public Response holdAvailableSeat(String token, String flightId, List<String> seatIds, Integer holdTtlSec) {
        for (String seatId : seatIds) {
            Response response = holdBooking(token, "flight", flightId, List.of(seatId), holdTtlSec);
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
