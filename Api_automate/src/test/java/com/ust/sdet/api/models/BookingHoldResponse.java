package com.ust.sdet.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingHoldResponse {
    @JsonProperty("id")
    private String holdId;

    private String inventoryId;
    private String[] seatIds;

    @JsonProperty("state")
    private String status;

    @JsonProperty("holdExpiresAt")
    private String expiryTime;

    private Long amountPaise;

    public String getHoldId() {
        return holdId;
    }

    public String getStatus() {
        return status;
    }

    public String[] getSeatIds() {
        return seatIds;
    }

    public Long getAmountPaise() {
        return amountPaise;
    }

    public String getExpiryTime() {
        return expiryTime;
    }
}
