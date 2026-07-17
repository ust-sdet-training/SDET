package API_FrameWork.config;

public final class EndPoints {

    private EndPoints() {
    }

    // =========================
    // Authentication APIs
    // =========================
    public static final String LOGIN = "/api/auth/login";
    public static final String AUTH_ME = "/api/auth/me";
    public static final String ADMIN_PING = "/api/auth/admin-ping";

    // =========================
    // Flight APIs
    // =========================
    public static final String FLIGHTS = "/api/flights";
    public static final String FLIGHT_SEATS = "/api/flights/{id}/seats";

    // =========================
    // Booking APIs
    // =========================
    public static final String BOOKINGS = "/api/bookings";
    public static final String BOOKING_BY_PNR = "/api/bookings/{pnr}";
    public static final String PAY_BOOKING = "/api/bookings/{id}/pay";
    public static final String CONFIRM_BOOKING = "/api/bookings/{id}/confirm";
    public static final String CANCEL_BOOKING = "/api/bookings/{id}/cancel";

    // Returns all bookings of the logged-in user
    public static final String MY_BOOKINGS = "/api/bookings";

    // =========================
    // Operations APIs
    // =========================
    public static final String RESET = "/api/reset";
}