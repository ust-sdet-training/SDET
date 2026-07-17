package com.ust.sdet.api.services;

import com.ust.sdet.api.client.ApiClient;
import com.ust.sdet.api.config.ConfigManager;
import com.ust.sdet.api.models.BookingConfirmResponse;
import com.ust.sdet.api.models.BookingHoldRequest;
import com.ust.sdet.api.models.BookingHoldResponse;
import com.ust.sdet.api.models.BookingRetrieveResponse;
import com.ust.sdet.api.models.PaymentResponse;
import com.ust.sdet.api.specs.RequestSpecFactory;
import io.restassured.response.Response;

public class BookingService {
    private final ApiClient apiClient;
    private final RequestSpecFactory requestSpecFactory;
    private final ConfigManager configManager;
    private final String token;

    public BookingService(ApiClient apiClient, RequestSpecFactory requestSpecFactory, ConfigManager configManager, String token) {
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

    public PaymentResponse processPayment(String holdId) {
        Response response = apiClient.post(
                "/api/bookings/" + holdId + "/pay",
                null,  // Body is ignored; send null or empty JSON
                requestSpecFactory.buildWithAuth(configManager, token)
        );

        response.then().statusCode(200);
        return response.as(PaymentResponse.class);
    }

    public BookingConfirmResponse confirmBooking(String holdId) {
        Response response = apiClient.post(
                "/api/bookings/" + holdId + "/confirm",
                null,  // Body is ignored; endpoint takes no body
                requestSpecFactory.buildWithAuth(configManager, token)
        );

        response.then().statusCode(200);
        return response.as(BookingConfirmResponse.class);
    }

    public BookingRetrieveResponse getBookingByPnr(String pnr) {
        Response response = apiClient.get(
                "/api/bookings/" + pnr,
                requestSpecFactory.buildWithAuth(configManager, token)
        );

        response.then().statusCode(200);
        return response.as(BookingRetrieveResponse.class);
    }
}
