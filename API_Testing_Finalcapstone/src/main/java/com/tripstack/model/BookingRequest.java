package com.tripstack.model;

import java.util.List;

public class BookingRequest {

    private String journeyType;
    private String inventoryId;
    private List<String> seatIds;
    private Boolean refundable;
    private Integer holdTtlSec;

    public BookingRequest() {
    }

    public BookingRequest(String journeyType, String inventoryId, List<String> seatIds, Boolean refundable, Integer holdTtlSec) {
        this.journeyType = journeyType;
        this.inventoryId = inventoryId;
        this.seatIds = seatIds;
        this.refundable = refundable;
        this.holdTtlSec = holdTtlSec;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public List<String> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<String> seatIds) {
        this.seatIds = seatIds;
    }

    public Boolean getRefundable() {
        return refundable;
    }

    public void setRefundable(Boolean refundable) {
        this.refundable = refundable;
    }

    public Integer getHoldTtlSec() {
        return holdTtlSec;
    }

    public void setHoldTtlSec(Integer holdTtlSec) {
        this.holdTtlSec = holdTtlSec;
    }
}
