package API_FrameWork.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
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
        String holdExpiresAt) {
}
