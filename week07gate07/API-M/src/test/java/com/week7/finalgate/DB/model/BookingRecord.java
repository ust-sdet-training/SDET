package com.week7.finalgate.DB.model;

public class BookingRecord {

    private String bookingId;
    private String pnr;
    private String employeeId;
    private String state;
    private String seatId;
    private int amountPaise;

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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public int getAmountPaise() {
        return amountPaise;
    }

    public void setAmountPaise(int amountPaise) {
        this.amountPaise = amountPaise;
    }
}