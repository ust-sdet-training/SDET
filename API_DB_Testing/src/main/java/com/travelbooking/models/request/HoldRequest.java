package com.travelbooking.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HoldRequest {

    private String journeyType;
    private String inventoryId;
    private List<String> seatIds;
    private Boolean refundable;
    private Integer holdTtlSec;

    public HoldRequest() {
    }

    public HoldRequest(String journeyType,
                       String inventoryId,
                       List<String> seatIds,
                       Boolean refundable,
                       Integer holdTtlSec) {
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