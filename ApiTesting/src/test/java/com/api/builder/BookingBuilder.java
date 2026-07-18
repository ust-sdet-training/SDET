package com.api.builder;

import com.api.model.Booking;

public class BookingBuilder {

    private Long id = 1L;
    private String pnr = "TS-1008-0001";
    private String state = "CONFIRMED";
    private String inventoryId = "BUS-CCUDEL-02";
    private String empId = "1008";

    public static BookingBuilder newBooking() {
        return new BookingBuilder();
    }

    public BookingBuilder withPnr(String pnr) {
        this.pnr = pnr;
        return this;
    }

    public BookingBuilder withState(String state) {
        this.state = state;
        return this;
    }

    public BookingBuilder withInventory(String inventoryId) {
        this.inventoryId = inventoryId;
        return this;
    }

    public BookingBuilder withEmployee(String empId) {
        this.empId = empId;
        return this;
    }

    public Booking build() {

        return new Booking(
                id,
                pnr,
                state,
                inventoryId,
                empId
        );
    }
}