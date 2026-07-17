package constants;


public final class ApiEndpoints {


    private ApiEndpoints() {
    }



    public static final String LOGIN =
            "/api/auth/login";



    public static final String BOOKINGS =
            "/api/bookings";



    public static final String PAY_BOOKING =
            "/api/bookings/{bookingId}/pay";



    public static final String CONFIRM_BOOKING =
            "/api/bookings/{bookingId}/confirm";



    public static final String GET_BOOKING =
            "/api/bookings/{pnr}";


}