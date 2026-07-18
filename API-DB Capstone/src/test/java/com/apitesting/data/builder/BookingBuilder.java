package com.apitesting.data.builder;

import com.apitesting.data.model.BookingRequest;

import java.util.ArrayList;
import java.util.List;

public class BookingBuilder {

    private String journeyType = "flight";
    private String inventoryId;
    private List<String> seatIds = new ArrayList<>();
    private boolean refundable = true;
    private int holdTtlSec = 120;

    private BookingBuilder() {
    }

    public static BookingBuilder aBooking() {
        return new BookingBuilder();
    }


    public BookingBuilder inventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
        return this;
    }

    public BookingBuilder seat(String seat) {
        this.seatIds.add(seat);
        return this;
    }

    public BookingRequest build() {

        return new BookingRequest(journeyType, inventoryId, seatIds, refundable, holdTtlSec);
    }
}