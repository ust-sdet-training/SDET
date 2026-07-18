package com.week7.finalgate.DB.model;

public class BookingSeatRecord {

    private Long id;

    private String bookingUuid;

    private String seatId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingUuid() {
        return bookingUuid;
    }

    public void setBookingUuid(String bookingUuid) {
        this.bookingUuid = bookingUuid;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }
}