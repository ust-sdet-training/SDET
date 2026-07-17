package com.tripstack.api.request;

public class BookingRequestBuilder {
    public static String build(String flightId, String passengerName) {
        return String.format("{\"flightId\":\"%s\",\"passengerName\":\"%s\"}", flightId, passengerName);
    }
}
