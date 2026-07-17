package models;

import java.util.List;

public class BookingRequest {

    private String journeyType;
    private String inventoryId;
    private List<String> seatIds;
    private boolean refundable;
    private int holdTtlSec;

    public BookingRequest(
            String journeyType,
            String inventoryId,
            List<String> seatIds,
            boolean refundable,
            int holdTtlSec
    ) {
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

    public int getHoldTtlSec() {
        return holdTtlSec;
    }
}