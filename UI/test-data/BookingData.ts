export class BookingData {

    // ==========================
    // Application
    // ==========================

    static readonly BASE_URL =
        "https://tripstack.doomple.com";

    // ==========================
    // Login
    // ==========================

    static readonly EMAIL =
        "uma@tripstack.test";

    static readonly PASSWORD =
        "Password@123";

    // ==========================
    // Journey Details
    // ==========================

    static readonly JOURNEY_TYPE =
        "Flight";

    // -------- Journey 1 --------

    static readonly FIRST_SOURCE =
        "MAA";

    static readonly FIRST_DESTINATION =
        "HYD";

    static readonly FIRST_TRAVEL_AFTER_DAYS =
        6;

    // -------- Journey 2 --------

    static readonly SECOND_SOURCE =
        "HYD";

    static readonly SECOND_DESTINATION =
        "BLR";

    static readonly SECOND_TRAVEL_AFTER_DAYS =
        7;

    // ==========================
    // Passenger Details
    // ==========================

    static readonly FIRST_NAME =
        "Mouni";

    static readonly LAST_NAME =
        "Test";

    static readonly AGE =
        "22";

    static readonly GENDER =
        "female";

    static readonly PHONE =
        "9876543210";

    static readonly CONTACT_EMAIL =
        BookingData.EMAIL;

    // ==========================
    // Payment
    // ==========================

    static readonly CARD_NAME =
        "Mouni Test";

    static readonly CARD_NUMBER =
        "4111111111111111";

    static readonly CARD_EXPIRY =
        "12/30";

    static readonly CARD_CVV =
        "123";

    // ==========================
    // Booking Validation
    // ==========================

    static readonly EXPECTED_AMOUNT =
        "₹4790.80";

    static readonly BOOKING_STATUS =
        "CONFIRMED";

    static readonly PNR_PREFIX =
        "TS-1021-";

    // ==========================
    // Runtime Values
    // These will be populated during execution.
    // ==========================
    

    static firstBookingId = "";

    static secondBookingId = "";

    static firstPNR = "";

    static secondPNR = "";

    static firstSeat = "";

    static secondSeat = "";

    static firstInventoryId = "";

    static secondInventoryId = "";

    // ==========================
    // Assignment Details
    // ==========================

    static readonly EMPLOYEE_ID =
        "1021";

    static readonly TRACK =
        "T3";

    static readonly FAULT =
        "Seat-hold expiry";

    static readonly PERFORMANCE_TARGET =
        "Seat-map render";

    static readonly SECURITY_TEST =
        "Expired token";

}