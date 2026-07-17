package models;


import java.util.List;


public record BookingResponse(

        String id,

        String pnr,

        String empId,

        String journeyType,

        String inventoryId,

        String state,

        List<String> seatIds,

        int amountPaise,

        boolean refundable,

        String holdExpiresAt

) {
}