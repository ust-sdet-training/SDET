package com.ust.sdet.api.models;

public class BookingHoldRequest {
    private String journeyType;
    private String inventoryId;
    private String[] seatIds;
    private boolean refundable = true;  // Default to true (most fares are refundable)

    public BookingHoldRequest(String journeyType, String inventoryId, String[] seatIds) {
        this.journeyType = journeyType;
        this.inventoryId = inventoryId;
        this.seatIds = seatIds;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public String[] getSeatIds() {
        return seatIds;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }
}
