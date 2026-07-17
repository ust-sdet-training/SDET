package com.ust.sdet.api.services;

import com.ust.sdet.api.client.ApiClient;
import com.ust.sdet.api.config.ConfigManager;
import com.ust.sdet.api.models.BusSearchResponse;
import com.ust.sdet.api.models.BusSeatMap;
import com.ust.sdet.api.specs.RequestSpecFactory;
import io.restassured.response.Response;

import java.util.Map;

public class BusService {
    private final ApiClient apiClient;
    private final RequestSpecFactory requestSpecFactory;
    private final ConfigManager configManager;

    public BusService(ApiClient apiClient, RequestSpecFactory requestSpecFactory, ConfigManager configManager) {
        this.apiClient = apiClient;
        this.requestSpecFactory = requestSpecFactory;
        this.configManager = configManager;
    }

    public BusSearchResponse searchBuses(String from, String to, String date) {
        Response response = apiClient.get(
                "/api/buses?from=" + from + "&to=" + to + "&date=" + date,
                requestSpecFactory.build(configManager)
        );

        response.then().statusCode(200);
        return response.as(BusSearchResponse.class);
    }

    public BusSeatMap getSeatMap(String busId) {
        Response response = apiClient.get(
                "/api/buses/" + busId + "/seats",
                requestSpecFactory.build(configManager)
        );

        response.then().statusCode(200);
        return response.as(BusSeatMap.class);
    }
}
