package com.tripstack.client;

import java.util.HashMap;
import java.util.Map;

import com.tripstack.core.BaseApiClient;
import com.tripstack.endpoints.Endpoints;

import io.restassured.response.Response;

public class FlightClient extends BaseApiClient {

    public Response searchFlights(String from, String to, String date, int pax, String cabinClass) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("from", from);
        queryParams.put("to", to);
        queryParams.put("date", date);
        queryParams.put("pax", pax);
        queryParams.put("class", cabinClass);
        return get(Endpoints.SEARCH_FLIGHTS, queryParams);
    }

    public Response getSeatMap(String flightId) {
        String endpoint = String.format(Endpoints.SEAT_MAP, flightId);
        return get(endpoint);
    }
}
