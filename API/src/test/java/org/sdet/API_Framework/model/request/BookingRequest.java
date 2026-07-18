package org.sdet.API_Framework.model.request;

import java.util.List;

public class BookingRequest {

    private String journeyType;
    private String inventoryId;
    private List<String> seatIds;
    private boolean refundable;
    private int holdTtlSec;

    public BookingRequest() {
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

    public boolean isRefundable() {
        return refundable;
    }

    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }

    public int getHoldTtlSec() {
        return holdTtlSec;
    }

    public void setHoldTtlSec(int holdTtlSec) {
        this.holdTtlSec = holdTtlSec;
    }
}