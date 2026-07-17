package org.sdet.model.response;

public class BookingResponse {

    private String bookingId;
    private String pnr;
    private String status;
    private String tripId;

    public BookingResponse() {
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    @Override
    public String toString() {
        return "BookingResponse{" +
                "bookingId='" + bookingId + '\'' +
                ", pnr='" + pnr + '\'' +
                ", status='" + status + '\'' +
                ", tripId='" + tripId + '\'' +
                '}';
    }
}