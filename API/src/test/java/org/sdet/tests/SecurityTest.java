package org.sdet.tests;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sdet.API_Framework.clients.BookingClient;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityTest {

    @Test
    @DisplayName("Verify user cannot access another user's booking")
    void shouldNotAccessOtherUsersBooking() {

        String pnr = "";

        BookingClient bookingClient= new BookingClient();

        Response response =
                bookingClient.getBookingByPNR(pnr);

        assertTrue(
                response.statusCode() == 403 ||
                        response.statusCode() == 404
        );
    }

}
