package com.week7.finalgate.API.service;

import com.week7.finalgate.API.Factory.RequestFactory;
import com.week7.finalgate.API.config.Endpoints;
import com.week7.finalgate.API.models.BookingRequest;
import com.week7.finalgate.API.models.BookingResponse;
import com.week7.finalgate.API.support.ApiContext;

import io.restassured.response.Response;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class BookingService {

    private final ApiContext context;

    public BookingService(ApiContext context) {
        this.context = context;
    }

    public BookingResponse holdSeat() {

        BookingRequest request =
                new BookingRequest(
                        "flight",
                        context.getFlightId(),
                        Collections.singletonList(
                                context.getSelectedSeat()
                        ),
                        true,
                        120
                );

        Response response =
                RequestFactory
                        .authorizedRequest(context.getToken())
                        .body(request)
                        .post(Endpoints.BOOKINGS);

        assertEquals(201, response.statusCode());

        BookingResponse booking =
                response.as(BookingResponse.class);

        context.setBookingId(
                booking.getId()
        );

        return booking;
    }

    public BookingResponse pay() {

        String endpoint =
                String.format(
                        Endpoints.PAY,
                        context.getBookingId()
                );

        Response response =
                RequestFactory
                        .authorizedRequest(context.getToken())
                        .body("{}")
                        .post(endpoint);

        assertEquals(200, response.statusCode());

        return response.as(
                BookingResponse.class
        );
    }

    public BookingResponse confirm() {

        String endpoint =
                String.format(
                        Endpoints.CONFIRM,
                        context.getBookingId()
                );

        Response response =
                RequestFactory
                        .authorizedRequest(context.getToken())
                        .post(endpoint);

        assertEquals(200, response.statusCode());

        BookingResponse booking =
                response.as(
                        BookingResponse.class
                );

        context.setPnr(
                booking.getPnr()
        );

        return booking;
    }

}