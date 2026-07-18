package API_FrameWork.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse {

    private String id;
    private String pnr;
    private String empId;
    private String journeyType;
    private String inventoryId;
    private String state;
    private List<String> seatIds;
    private int amountPaise;
    private boolean refundable;
    private String holdExpiresAt;
    // Getters
    public String getId() {
        return id;
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
    public String getInventoryId() {
        return inventoryId;
    }
    public String getState() {
        return state;
    }
    public List<String> getSeatIds() {
        return seatIds;
    }
    public int getAmountPaise() {
        return amountPaise;
    }
    public boolean isRefundable() {
        return refundable;
    }
    public String getHoldExpiresAt() {
        return holdExpiresAt;
    }
}