package api.tripstack.clients;

import api.tripstack.constants.ApiRoutes;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class BusClient {
    public Response search(String from, String to, String date) {
        return given().queryParam("from", from).queryParam("to", to).queryParam("date", date).get(ApiRoutes.BUSES);
    }

    public Response seatMap(String busId) { return given().get(ApiRoutes.BUS_SEATS, busId); }

    public String firstAvailableAcSemiBus(Response response) {
        List<Map<String, Object>> buses = response.jsonPath().getList("buses");
        return buses.stream()
                .filter(bus -> "ac-semi".equals(bus.get("kind")) && ((Number) bus.get("seatsLeft")).intValue() > 0)
                .map(bus -> (String) bus.get("id"))
                .findFirst().orElseThrow(() -> new IllegalStateException("No available HYD-BOM AC Semi-Sleeper bus"));
    }

    public String firstAvailableSeat(Response response) {
        List<Map<String, Object>> lower = response.jsonPath().getList("decks.lower");
        List<Map<String, Object>> upper = response.jsonPath().getList("decks.upper");
        return Stream.concat(lower.stream(), upper.stream())
                .filter(seat -> "available".equals(seat.get("state")))
                .map(seat -> (String) seat.get("seatId"))
                .findFirst().orElseThrow(() -> new IllegalStateException("No available bus seat"));
    }
}
