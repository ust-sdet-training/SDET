package com.routepulse.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponse {
    @JsonProperty("id")
    private String transactionId;
    
    @JsonProperty("state")
    private String status;
    
    private String holdId;
    private Integer amountPaise;

    public String getTransactionId() {
        return transactionId;
    }

    public String getHoldId() {
        return holdId;
    }

    public String getStatus() {
        return status;
    }

    public Integer getAmountPaise() {
        return amountPaise;
    }
}
