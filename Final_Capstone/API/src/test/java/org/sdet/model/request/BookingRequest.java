package org.sdet.model.request;

public class BookingRequest {

    private String tripId;
    private String seatNumber;
    private String passengerName;

    public BookingRequest() {
    }

    public BookingRequest(String tripId, String seatNumber, String passengerName) {
        this.tripId = tripId;
        this.seatNumber = seatNumber;
        this.passengerName = passengerName;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
}