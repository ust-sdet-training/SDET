package API_FrameWork.models;

import java.util.List;

public record BookingRequest(
        String journeyType,
        String inventoryId,
        List<String> seatIds,
        boolean refundable,
        int holdTtlSec) {
}
