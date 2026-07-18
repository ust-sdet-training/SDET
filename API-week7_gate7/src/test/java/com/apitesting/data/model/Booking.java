package com.apitesting.data.model;

public class Booking {

    private String bookingId;
    private String pnr;
    private String empId;
    private String journeyType;
    private String sourceCity;
    private String destinationCity;
    private String bookingStatus;

    public Booking(
            String bookingId,
            String pnr,
            String empId,
            String journeyType,
            String sourceCity,
            String destinationCity,
            String bookingStatus
    ) {
        this.bookingId = bookingId;
        this.pnr = pnr;
        this.empId = empId;
        this.journeyType = journeyType;
        this.sourceCity = sourceCity;
        this.destinationCity = destinationCity;
        this.bookingStatus = bookingStatus;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getPnr() {
        return pnr;
    }

    public String getEmpId() {
        return empId;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }
}
