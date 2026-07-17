package models;


import java.util.List;


public record BookingRequest(

        String journeyType,

        String inventoryId,

        List<String> seatIds,

        boolean refundable

) {
}