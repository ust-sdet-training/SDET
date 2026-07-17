package com.api.stepdefs;

import com.api.clients.BusClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class BusStep {

    private final BusClient busClient;

    public BusStep() {
        this.busClient = new BusClient();
    }

    @Step("Search buses from {0} to {1} on {2}")
    public Response searchBuses(String from, String to, String date) {
        return busClient.searchBuses(from, to, date);
    }

    @Step("Get seat map for bus {0}")
    public Response getSeatMap(String busId) {
        return busClient.getBusSeatMap(busId);
    }

    @Step("Get first available bus id from {0} to {1} on {2}")
    public String getFirstBusId(String from, String to, String date) {

        return searchBuses(from, to, date)
                .jsonPath()
                .getString("buses[0].id");
    }

    @Step("Get seat map of first available bus from {0} to {1}")
    public Response getSeatMapForAvailableBus(String from, String to, String date) {

        String busId = getFirstBusId(from, to, date);

        return getSeatMap(busId);
    }
}