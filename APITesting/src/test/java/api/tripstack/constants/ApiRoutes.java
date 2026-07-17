package api.tripstack.constants;

public final class ApiRoutes {
    public static final String LOGIN = "/api/auth/login";
    public static final String ME = "/api/auth/me";
    public static final String BUSES = "/api/buses";
    public static final String BUS_SEATS = "/api/buses/{id}/seats";
    public static final String BOOKINGS = "/api/bookings";
    public static final String RESET = "/api/reset";

    private ApiRoutes() { }

    public static String payment(String bookingId) {
        return BOOKINGS + "/" + bookingId + "/pay";
    }
    public static String confirmation(String bookingId) {
        return BOOKINGS + "/" + bookingId + "/confirm";
    }
}
