package com.routepulse.api.services;

import com.routepulse.api.client.HttpGateway;
import com.routepulse.api.config.TravelConfigManager;
import com.routepulse.api.models.BookingConfirmResponse;
import com.routepulse.api.models.BookingHoldRequest;
import com.routepulse.api.models.BookingHoldResponse;
import com.routepulse.api.models.BookingRetrieveResponse;
import com.routepulse.api.specs.RequestBlueprintFactory;
import io.restassured.response.Response;

public class ReservationService {
    private final HttpGateway apiClient;
    private final RequestBlueprintFactory requestSpecFactory;
    private final TravelConfigManager configManager;
    private final String token;

    public ReservationService(HttpGateway apiClient, RequestBlueprintFactory requestSpecFactory, TravelConfigManager configManager, String token) {
        this.apiClient = apiClient;
        this.requestSpecFactory = requestSpecFactory;
        this.configManager = configManager;
        this.token = token;
    }

    public BookingHoldResponse holdSeats(String journeyType, String inventoryId, String[] seatIds) {
        BookingHoldRequest request = new BookingHoldRequest(journeyType, inventoryId, seatIds);
        Response response = apiClient.post(
                "/api/bookings",
                request,
                requestSpecFactory.buildWithAuth(configManager, token)
        );
        response.then().statusCode(201);
        return response.as(BookingHoldResponse.class);
    }

    public BookingConfirmResponse payForBooking(String bookingId) {
        Response response = apiClient.post(
                "/api/bookings/" + bookingId + "/pay",
                "{}",
                requestSpecFactory.buildWithAuth(configManager, token)
        );
        response.then().statusCode(200);
        return response.as(BookingConfirmResponse.class);
    }

    public BookingConfirmResponse confirmBooking(String bookingId) {
        Response response = apiClient.post(
                "/api/bookings/" + bookingId + "/confirm",
                null,
                requestSpecFactory.buildWithAuth(configManager, token)
        );
        response.then().statusCode(200);
        return response.as(BookingConfirmResponse.class);
    }

    public BookingRetrieveResponse retrieveBooking(String pnr) {
        Response response = apiClient.get(
                "/api/bookings/" + pnr,
                requestSpecFactory.buildWithAuth(configManager, token)
        );
        response.then().statusCode(200);
        return response.as(BookingRetrieveResponse.class);
    }
}
