package org.sdet.API_Framework.builders;

import org.sdet.API_Framework.model.request.BookingRequest;

import java.util.List;

public final class BookingBuilder {

    private BookingBuilder() {
    }

    public static BookingRequest createBooking(String flightId,
                                               String seatNo) {

        BookingRequest request = new BookingRequest();

        request.setJourneyType("flight");
        request.setInventoryId(flightId);
        request.setSeatIds(List.of(seatNo));
        request.setRefundable(true);
        request.setHoldTtlSec(120);

        return request;
    }
}