package com.week7.finalgate.API.service;

import com.week7.finalgate.API.Factory.RequestFactory;
import com.week7.finalgate.API.config.Endpoints;
import com.week7.finalgate.API.models.Seat;
import com.week7.finalgate.API.models.SeatMapResponse;
import com.week7.finalgate.API.models.SeatRow;
import com.week7.finalgate.API.support.ApiContext;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.*;

public class SeatService {

    private final ApiContext context;

    public SeatService(ApiContext context) {
        this.context = context;
    }

    public Seat selectFirstAvailableSeat() {

        String endpoint =
                String.format(
                        Endpoints.FLIGHT_SEATS,
                        context.getFlightId()
                );

        Response response =
                RequestFactory.publicRequest()
                        .get(endpoint);

        assertEquals(200, response.statusCode());

        SeatMapResponse seatMap =
                response.as(SeatMapResponse.class);

        assertTrue(seatMap.getAvailable() > 0);

        for (SeatRow row : seatMap.getRows()) {

            for (Seat seat : row.getSeats()) {

                if (!seat.isOccupied()) {

                    context.setSelectedSeat(
                            seat.getSeatId()
                    );

                    return seat;

                }

            }

        }

        throw new RuntimeException(
                "No seats available"
        );

    }

}
