package com.week7.finalgate.API.service;

import com.week7.finalgate.API.Factory.RequestFactory;
import com.week7.finalgate.API.config.Endpoints;
import com.week7.finalgate.API.models.Flight;
import com.week7.finalgate.API.models.FlightSearchResponse;
import com.week7.finalgate.API.support.ApiContext;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;

public class FlightService {

    private final ApiContext context;

    public FlightService(ApiContext context) {
        this.context = context;
    }

    public Flight searchFlight(
            String from,
            String to,
            String date
    ) {

        Response response =
                RequestFactory.publicRequest()
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .queryParam("date", date)
                        .get(Endpoints.FLIGHTS);

        assertEquals(200, response.statusCode());

        FlightSearchResponse searchResponse =
                response.as(FlightSearchResponse.class);

        assertTrue(searchResponse.getCount() > 0);

        Flight firstFlight =
                searchResponse.getFlights().get(0);

        context.setFlightId(firstFlight.getId());

        return firstFlight;

    }

}