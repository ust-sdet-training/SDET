package com.week7.finalgate.API.service;

import com.week7.finalgate.API.Factory.RequestFactory;
import com.week7.finalgate.API.config.Endpoints;
import com.week7.finalgate.API.models.BookingResponse;
import com.week7.finalgate.API.support.ApiContext;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingListService {

    private final ApiContext context;

    public BookingListService(ApiContext context) {
        this.context = context;
    }

    public List<BookingResponse> getMyBookings() {

        Response response =
                RequestFactory
                        .authorizedRequest(context.getToken())
                        .get(Endpoints.BOOKINGS);

        assertEquals(200, response.statusCode());

        return response.as(new TypeRef<List<BookingResponse>>() {});
    }

}