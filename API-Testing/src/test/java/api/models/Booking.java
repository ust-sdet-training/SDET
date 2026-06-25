package api.models;

public record Booking (
        String firstname,
        String latname,
        Number totalprice,
        Boolean depositpaid,
        BookingDates bookingdates,
        String additionalneeds
){}
