package com.week7.finalgate.API.service;

import com.week7.finalgate.API.Factory.RequestFactory;
import com.week7.finalgate.API.config.Endpoints;
import com.week7.finalgate.API.models.BookingResponse;
import com.week7.finalgate.API.support.ApiContext;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingDetailsService {

    private final ApiContext context;

    public BookingDetailsService(ApiContext context) {
        this.context = context;
    }

    public BookingResponse getBookingByPNR() {

        String endpoint =
                String.format(
                        Endpoints.BOOKING_BY_PNR,
                        context.getPnr()
                );

        Response response =
                RequestFactory
                        .authorizedRequest(context.getToken())
                        .get(endpoint);

        assertEquals(200, response.statusCode());

        return response.as(BookingResponse.class);
    }

}