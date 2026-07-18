package API_FrameWork.config;

public final class TestData {
    private TestData() {
    }

    // Sample login details used by the tests
    public static final String EMAIL = "karl@tripstack.test";
    public static final String PASSWORD = "Password@123";

    // Sample travel request data
    public static final String FROM = "JAI";
    public static final String TO = "BOM";
    public static final String DATE = "2026-08-14";
    public static final String TRAVEL_CLASS = "economy";

    // Booking details used in the workflow test
    public static final String JOURNEY_TYPE = "flight";
    public static final boolean REFUNDABLE = true;
    public static final int HOLD_TIME = 120;
}