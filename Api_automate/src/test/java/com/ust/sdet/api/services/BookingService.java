package com.ust.sdet.api.services;

import com.ust.sdet.api.client.ApiClient;
import com.ust.sdet.api.config.ConfigManager;
import com.ust.sdet.api.models.*;
import com.ust.sdet.api.specs.RequestSpecFactory;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    public BookingHoldResponse holdSeats(String journeyType, String inventoryId, String[] seatIds, Integer ttlsec) {
        BookingHoldRequest request = new BookingHoldRequest(journeyType, inventoryId, seatIds, ttlsec);
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
                null,
                requestSpecFactory.buildWithAuth(configManager, token)
        );

        response.then().statusCode(200);
        return response.as(PaymentResponse.class);
    }

    public ErrorResponse cannotProcessPayment(String holdId) throws InterruptedException {
        Thread.sleep(5000);
        Response response = apiClient.post(
                "/api/bookings/" + holdId + "/pay",
                null,
                requestSpecFactory.buildWithAuth(configManager, token)
        );
        response.then().statusCode(409);

        return response.as(ErrorResponse.class);
    }

    public BookingConfirmResponse confirmBooking(String holdId) {
        Response response = apiClient.post(
                "/api/bookings/" + holdId + "/confirm",
                null,
                requestSpecFactory.buildWithAuth(configManager, token)
        );

        response.then().statusCode(200);
        return response.as(BookingConfirmResponse.class);
    }

    public com.ust.sdet.api.models.BookingRetrieveResponse getBookingByPnr(String pnr) {
        Response response = apiClient.get(
                "/api/bookings/" + pnr,
                requestSpecFactory.buildWithAuth(configManager, token)
        );

        response.then().statusCode(200);
        return response.as(com.ust.sdet.api.models.BookingRetrieveResponse.class);
    }
}
