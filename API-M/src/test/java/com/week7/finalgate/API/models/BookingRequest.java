package com.week7.finalgate.API.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class BookingRequest {

    private String journeyType;
    private String inventoryId;
    private List<String> seatIds;
    private boolean refundable;
    private Integer holdTtlSec;

    public BookingRequest() {
    }

    public BookingRequest(String journeyType,
                          String inventoryId,
                          List<String> seatIds,
                          boolean refundable,
                          Integer holdTtlSec) {

        this.journeyType = journeyType;
        this.inventoryId = inventoryId;
        this.seatIds = seatIds;
        this.refundable = refundable;
        this.holdTtlSec = holdTtlSec;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public List<String> getSeatIds() {
        return seatIds;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public Integer getHoldTtlSec() {
        return holdTtlSec;
    }
}