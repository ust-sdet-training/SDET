package com.ust.sdet.api.services;

import com.ust.sdet.api.client.ApiClient;
import com.ust.sdet.api.config.ConfigManager;
import com.ust.sdet.api.models.FlightSearchResponse;
import com.ust.sdet.api.models.FlightSeatMap;
import com.ust.sdet.api.specs.RequestSpecFactory;
import io.restassured.response.Response;

public class FlightService {
    private final ApiClient apiClient;
    private final RequestSpecFactory requestSpecFactory;
    private final ConfigManager configManager;

    public FlightService(ApiClient apiClient, RequestSpecFactory requestSpecFactory, ConfigManager configManager) {
        this.apiClient = apiClient;
        this.requestSpecFactory = requestSpecFactory;
        this.configManager = configManager;
    }

    public FlightSearchResponse searchFlights(String from, String to, String date) {
        Response response = apiClient.get(
                "/api/flights?from=" + from + "&to=" + to + "&date=" + date + "&pax=1&class=economy",
                requestSpecFactory.build(configManager)
        );

        response.then().statusCode(200);
        return response.as(FlightSearchResponse.class);
    }

    public FlightSeatMap getSeatMap(String flightId) {
        Response response = apiClient.get(
                "/api/flights/" + flightId + "/seats?class=economy",
                requestSpecFactory.build(configManager)
        );

        response.then().statusCode(200);
        return response.as(FlightSeatMap.class);
    }
}
