package org.sdet.builders;

import org.sdet.model.request.BookingRequest;

public class BookingBuilder {

    private BookingRequest request;

    public BookingBuilder() {
        request = new BookingRequest();
    }

    public BookingBuilder setTripId(String tripId) {
        request.setTripId(tripId);
        return this;
    }

    public BookingBuilder setSeatNumber(String seatNumber) {
        request.setSeatNumber(seatNumber);
        return this;
    }

    public BookingBuilder setPassengerName(String passengerName) {
        request.setPassengerName(passengerName);
        return this;
    }

    public BookingRequest build() {
        return request;
    }

}