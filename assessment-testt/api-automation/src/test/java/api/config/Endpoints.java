package api.config;

public final class Endpoints {
    public static final String AUTH_LOGIN = "/api/auth/login";
    public static final String AUTH_ADMIN_PING = "/api/auth/admin-ping";
    public static final String BUSES_SEARCH = "/api/buses";
    public static final String BUS_SEATS = "/api/buses/{id}/seats";
    public static final String BOOKINGS = "/api/bookings";
    public static final String BOOKING_PAY = "/api/bookings/{id}/pay";
    public static final String BOOKING_CONFIRM = "/api/bookings/{id}/confirm";
    public static final String BOOKING_BY_PNR = "/api/bookings/{pnr}";

    private Endpoints() {
        // constants only
    }
}
