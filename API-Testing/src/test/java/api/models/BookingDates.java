package api.models;

import java.util.Date;

public record BookingDates(
        Date checkin,
        Date checkout
) {
}
