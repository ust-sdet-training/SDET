package com.routepulse.api.services;

import com.routepulse.api.client.HttpGateway;
import com.routepulse.api.config.TravelConfigManager;
import com.routepulse.api.models.BusSearchResponse;
import com.routepulse.api.models.BusSeatMap;
import com.routepulse.api.specs.RequestBlueprintFactory;
import io.restassured.response.Response;

public class CoachService {
    private final HttpGateway apiClient;
    private final RequestBlueprintFactory requestSpecFactory;
    private final TravelConfigManager configManager;

    public CoachService(HttpGateway apiClient, RequestBlueprintFactory requestSpecFactory, TravelConfigManager configManager) {
        this.apiClient = apiClient;
        this.requestSpecFactory = requestSpecFactory;
        this.configManager = configManager;
    }

    public BusSearchResponse searchBuses(String from, String to, String date) {
        String path = "/api/buses?from=" + from + "&to=" + to + "&date=" + date;
        Response response = apiClient.get(path, requestSpecFactory.build(configManager));
        response.then().statusCode(200);
        return response.as(BusSearchResponse.class);
    }

    public BusSeatMap getSeatMap(String busId) {
        Response response = apiClient.get("/api/buses/" + busId + "/seats", requestSpecFactory.build(configManager));
        response.then().statusCode(200);
        return response.as(BusSeatMap.class);
    }
}
