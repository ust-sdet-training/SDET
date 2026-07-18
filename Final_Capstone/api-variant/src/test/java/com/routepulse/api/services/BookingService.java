package com.routepulse.api.services;

import com.routepulse.api.client.HttpGateway;
import com.routepulse.api.config.TravelConfigManager;
import com.routepulse.api.models.BookingConfirmResponse;
import com.routepulse.api.models.BookingHoldRequest;
import com.routepulse.api.models.BookingHoldResponse;
import com.routepulse.api.models.BookingRetrieveResponse;
import com.routepulse.api.models.PaymentResponse;
import com.routepulse.api.specs.RequestBlueprintFactory;
import io.restassured.response.Response;

public class BookingService {
    private final HttpGateway httpGateway;
    private final RequestBlueprintFactory requestBlueprintFactory;
    private final TravelConfigManager travelConfigManager;
    private final String token;

    public BookingService(HttpGateway httpGateway, RequestBlueprintFactory requestBlueprintFactory, TravelConfigManager travelConfigManager, String token) {
        this.httpGateway = httpGateway;
        this.requestBlueprintFactory = requestBlueprintFactory;
        this.travelConfigManager = travelConfigManager;
        this.token = token;
    }

    public BookingHoldResponse holdSeats(String journeyType, String inventoryId, String[] seatIds) {
        BookingHoldRequest request = new BookingHoldRequest(journeyType, inventoryId, seatIds);
        Response response = httpGateway.post(
                "/api/bookings",
                request,
                requestBlueprintFactory.buildWithAuth(travelConfigManager, token)
        );

        response.then().statusCode(201);
        return response.as(BookingHoldResponse.class);
    }

    public PaymentResponse processPayment(String holdId) {
        Response response = httpGateway.post(
                "/api/bookings/" + holdId + "/pay",
                null,  // Body is ignored; send null or empty JSON
                requestBlueprintFactory.buildWithAuth(travelConfigManager, token)
        );

        response.then().statusCode(200);
        return response.as(PaymentResponse.class);
    }

    public BookingConfirmResponse confirmBooking(String holdId) {
        Response response = httpGateway.post(
                "/api/bookings/" + holdId + "/confirm",
                null,  // Body is ignored; endpoint takes no body
                requestBlueprintFactory.buildWithAuth(travelConfigManager, token)
        );

        response.then().statusCode(200);
        return response.as(BookingConfirmResponse.class);
    }

    public BookingRetrieveResponse getBookingByPnr(String pnr) {
        Response response = httpGateway.get(
                "/api/bookings/" + pnr,
                requestBlueprintFactory.buildWithAuth(travelConfigManager, token)
        );

        response.then().statusCode(200);
        return response.as(BookingRetrieveResponse.class);
    }
}
