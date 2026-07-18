package API_FrameWork.config;

public final class EndPoints {
    private EndPoints() {
    }
    public static final String LOGIN = "/api/auth/login";
    public static final String AUTH_ME = "/api/auth/me";
    public static final String ADMIN_PING = "/api/auth/admin-ping";
    public static final String SEARCH_FLIGHTS = "/api/flights";
    public static final String SEAT_MAP = "/api/flights/{id}/seats";
    public static final String CREATE_BOOKING = "/api/bookings";
    public static final String GET_BOOKING = "/api/bookings/{pnr}";
    public static final String PAY_BOOKING = "/api/bookings/{id}/pay";
    public static final String CONFIRM_BOOKING = "/api/bookings/{id}/confirm";
    public static final String CANCEL_BOOKING = "/api/bookings/{id}/cancel";
}