package com.tripstack.models;

import java.util.List;

public class BookingResponse {

    private String id;
    private String pnr;
    private String empId;
    private String state;
    private String journeyType;
    private String inventoryId;
    private List<String> seatIds;
    private Integer amountPaise;

    public BookingResponse() {
    }

    public String getId() {
        return id;
    }

    public String getPnr() {
        return pnr;
    }

    public String getEmpId() {
        return empId;
    }

    public String getState() {
        return state;
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

    public Integer getAmountPaise() {
        return amountPaise;
    }
}