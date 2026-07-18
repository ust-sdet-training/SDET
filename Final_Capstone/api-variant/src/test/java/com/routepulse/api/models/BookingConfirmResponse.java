package com.routepulse.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingConfirmResponse {
    private String pnr;
    
    @JsonProperty("state")
    private String status;
    
    @JsonProperty("id")
    private String holdId;
    
    private String[] seatIds;
    
    @JsonProperty("amountPaise")
    private Integer totalFarePaise;

    public String getPnr() {
        return pnr;
    }

    public String getStatus() {
        return status;
    }

    public String getHoldId() {
        return holdId;
    }

    public String[] getSeatIds() {
        return seatIds;
    }

    public Integer getTotalFarePaise() {
        return totalFarePaise;
    }
}
