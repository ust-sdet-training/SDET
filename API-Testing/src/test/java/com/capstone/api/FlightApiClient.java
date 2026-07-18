package com.capstone.api;

import com.capstone.api.specs.RequestSpecs;
import com.capstone.api.specs.ResponseSpecs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class FlightApiClient {
    private final RequestSpecification request;

    public FlightApiClient(RequestSpecification request) {
        this.request = request;
    }

    public Response searchFlights(String from, String to, String date, int pax, String journeyClass) {
        return given(request)
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("date", date)
                .queryParam("pax", pax)
                .queryParam("class", journeyClass)
                .when()
                .get("/flights");
    }

    public String selectFirstFlightId(String from, String to, String date, int pax, String journeyClass) {
        return searchFlights(from, to, date, pax, journeyClass)
                .then()
                .spec(ResponseSpecs.ok200())
                .extract()
                .path("flights[0].id");
    }

    public Response getSeatMap(String flightId) {
        return given(request)
                .pathParam("id", flightId)
                .when()
                .get("/flights/{id}/seats");
    }

    public List<String> selectAvailableSeatIds(String flightId, String journeyClass) {
        Response seatMap = getSeatMap(flightId)
                .then()
                .spec(ResponseSpecs.ok200())
                .extract()
                .response();

        List<String> availableSeatIds = new ArrayList<>();
        List<Map<String, Object>> rows = seatMap.jsonPath().getList("rows");

        for (Map<String, Object> row : rows) {
            List<Map<String, Object>> seats = (List<Map<String, Object>>) row.get("seats");
            for (Map<String, Object> seat : seats) {
                Boolean occupied = (Boolean) seat.get("occupied");
                if (!occupied) {
                    String seatId = seat.get("seat_id").toString();
                    availableSeatIds.add(seatId);
                }
            }
        }

        if (availableSeatIds.isEmpty()) {
            throw new RuntimeException("No available seats found.");
        }
        return availableSeatIds;
    }
}
