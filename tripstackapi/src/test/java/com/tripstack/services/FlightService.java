package com.tripstack.services;

import com.tripstack.base.RequestSpecificationBuilder;
import com.tripstack.constants.ApiEndpoints;
import com.tripstack.model.request.FlightSearchRequest;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class FlightService {

    public Response searchFlights(FlightSearchRequest request) {

        return given()
                .spec(RequestSpecificationBuilder.requestSpecification())
                .queryParam("from", request.getFrom())
                .queryParam("to", request.getTo())
                .queryParam("date", request.getDate())
                .queryParam("pax", request.getPassengers())
                .queryParam("class", request.getTravelClass())
                .when()
                .get(ApiEndpoints.FLIGHTS);
    }

    public Response getSeatMap(String flightId) {

        return given()
                .spec(RequestSpecificationBuilder.requestSpecification())
                .pathParam("id", flightId)
                .when()
                .get(ApiEndpoints.FLIGHT_SEATS);
    }

    public String getFirstFlightId(Response response) {

        List<String> ids = response.jsonPath().getList("flights.id");

        if (ids == null || ids.isEmpty()) {
            throw new RuntimeException("No flights found.");
        }

        return ids.get(0);
    }

    @SuppressWarnings("unchecked")
    public String getFirstAvailableSeat(Response response) {

        List<Map<String, Object>> rows = response.jsonPath().getList("rows");

        for (Map<String, Object> row : rows) {

            List<Map<String, Object>> seats =
                    (List<Map<String, Object>>) row.get("seats");

            for (Map<String, Object> seat : seats) {

                Boolean occupied = (Boolean) seat.get("occupied");

                if (!occupied) {
                    return seat.get("seat_id").toString();
                }

            }

        }

        throw new RuntimeException("No available seat found.");

    }

}