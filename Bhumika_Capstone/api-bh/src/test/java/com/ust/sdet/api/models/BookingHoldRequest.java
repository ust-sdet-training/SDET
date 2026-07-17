package com.ust.sdet.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingHoldRequest {
    @JsonProperty("journeyType")
    private final String journeyType;

    @JsonProperty("inventoryId")
    private final String inventoryId;

    @JsonProperty("seatIds")
    private final String[] seatIds;

    public BookingHoldRequest(String journeyType, String inventoryId, String[] seatIds) {
        this.journeyType = journeyType;
        this.inventoryId = inventoryId;
        this.seatIds = seatIds;
    }
}
