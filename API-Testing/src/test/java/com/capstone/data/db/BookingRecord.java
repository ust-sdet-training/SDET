package com.capstone.data.db;

public record BookingRecord(
        String id,
        String pnr,
        String empId,
        String journeyType,
        String inventoryId,
        String state,
        String seatIds,
        Long amountPaise,
        boolean refundable
) {
}
