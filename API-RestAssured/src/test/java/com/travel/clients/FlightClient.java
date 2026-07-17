package com.travel.clients;

import com.travel.models.response.Flight;
import com.travel.models.response.FlightSearchResponse;
import com.travel.models.response.Row;
import com.travel.models.response.Seat;
import com.travel.models.response.SeatMapResponse;
import com.travel.specs.ResponseSpec;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;

public class FlightClient extends BaseAPIClient {

    public FlightSearchResponse search(String from, String to, String date) {
        return given()
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("date", date)
                .queryParam("pax", 1)
                .queryParam("cls", "economy")
                .when()
                .get("/flights")
                .then()
                .spec(ResponseSpec.ok())
                .extract()
                .as(FlightSearchResponse.class);
    }

    public Flight firstFlight(String from, String to) {
        return search(from, to, LocalDate.now().plusDays(9).toString()).flights().get(0);
    }

    public SeatMapResponse seatMap(String flightId) {
        return get("/flights/" + flightId + "/seats").then().spec(ResponseSpec.ok()).extract().as(SeatMapResponse.class);
    }

    public Seat firstAvailableSeat(String flightId) {
        SeatMapResponse map = seatMap(flightId);
        for (Row row : map.rows()) {
            for (Seat seat : row.seats()) {
                if (!seat.occupied()) {
                    return seat;
                }
            }
        }
        throw new RuntimeException("No available seat found.");
    }
}