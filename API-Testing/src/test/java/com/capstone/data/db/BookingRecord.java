package com.capstone.data.db;

public record BookingRecord(
        String id,
        String pnr,
        String empId,
        String journeyType,
        String inventoryId,
        String state,
        Integer amountPaise,
        boolean refundable
) {
}
