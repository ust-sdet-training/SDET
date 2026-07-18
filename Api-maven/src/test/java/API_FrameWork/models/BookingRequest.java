package API_FrameWork.models;

import java.util.List;

public class BookingRequest {
    private String journeyType;
    private String inventoryId;
    private List<String> seatIds;
    private boolean refundable;
    private int holdTtlSec;
    // Getters
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
    // Setters
    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }
    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }
    public void setSeatIds(List<String> seatIds) {
        this.seatIds = seatIds;
    }
    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }
    public void setHoldTtlSec(int holdTtlSec) {
        this.holdTtlSec = holdTtlSec;
    }
}