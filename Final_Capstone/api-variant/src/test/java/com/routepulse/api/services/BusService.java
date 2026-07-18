package com.routepulse.api.services;

import com.routepulse.api.client.HttpGateway;
import com.routepulse.api.config.TravelConfigManager;
import com.routepulse.api.models.BusSearchResponse;
import com.routepulse.api.models.BusSeatMap;
import com.routepulse.api.specs.RequestBlueprintFactory;
import io.restassured.response.Response;

import java.util.Map;

public class BusService {
    private final HttpGateway httpGateway;
    private final RequestBlueprintFactory requestBlueprintFactory;
    private final TravelConfigManager travelConfigManager;

    public BusService(HttpGateway httpGateway, RequestBlueprintFactory requestBlueprintFactory, TravelConfigManager travelConfigManager) {
        this.httpGateway = httpGateway;
        this.requestBlueprintFactory = requestBlueprintFactory;
        this.travelConfigManager = travelConfigManager;
    }

    public BusSearchResponse searchBuses(String from, String to, String date) {
        Response response = httpGateway.get(
                "/api/buses?from=" + from + "&to=" + to + "&date=" + date,
                requestBlueprintFactory.build(travelConfigManager)
        );

        response.then().statusCode(200);
        return response.as(BusSearchResponse.class);
    }

    public BusSeatMap getSeatMap(String busId) {
        Response response = httpGateway.get(
                "/api/buses/" + busId + "/seats",
                requestBlueprintFactory.build(travelConfigManager)
        );

        response.then().statusCode(200);
        return response.as(BusSeatMap.class);
    }
}
