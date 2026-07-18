package tests;

import api.clients.BookingClient;
import io.restassured.response.Response;
import models.BookingPnrRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import security.SecurityConstants;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SecurityBolaTest {
    private final BookingClient bookingClient = new BookingClient();
    @Test
    @DisplayName("user cannot access other employee booking")
    void shouldNotReadAnotherEmployeesBooking() {
        BookingPnrRequest request = new BookingPnrRequest(SecurityConstants.OTHER_EMPLOYEE_PNR);
        Response response = bookingClient.readBookingByPnr(request);
        assertEquals(403, response.statusCode(), "user should not be able to access other employee booking");
    }
}
