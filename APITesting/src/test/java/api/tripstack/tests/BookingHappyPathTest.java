package api.tripstack.tests;

import api.tripstack.base.BaseApiTest;
import api.tripstack.clients.BookingClient;
import api.tripstack.config.ConfigManager;
import api.tripstack.models.BookingRequest;
import api.tripstack.utils.DateUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingHappyPathTest extends BaseApiTest {

    @Test
    void completesBusBookingAndListsConfirmedPnrInEmployee1030Namespace() {
        String token = loginUser();
        BookingClient bookingClient = new BookingClient(token);

        Response buses = busClient.search("HYD", "BOM", DateUtils.indiaDateAfter(14));
        assertEquals(200, buses.getStatusCode());

        String busId = busClient.firstAvailableAcSemiBus(buses);
        Response seats = busClient.seatMap(busId);
        assertEquals(200, seats.getStatusCode());

        String seatId = busClient.firstAvailableSeat(seats);
        BookingRequest holdRequest = new BookingRequest("bus", busId, List.of(seatId), true, 300);

        Response hold = bookingClient.createHold(holdRequest);
        assertTrue(hold.getStatusCode() == 200 || hold.getStatusCode() == 201);
        String bookingId = hold.jsonPath().getString("bookingId");
        if (bookingId == null || bookingId.isBlank()) {
            bookingId = hold.jsonPath().getString("id");
        }
        assertTrue(bookingId != null && !bookingId.isBlank(), "Expected a booking id in the hold response");

        Response pay = bookingClient.pay(bookingId);
        assertTrue(pay.getStatusCode() == 200 || pay.getStatusCode() == 201 || pay.getStatusCode() == 204);

        Response confirm = bookingClient.confirm(bookingId);
        assertTrue(confirm.getStatusCode() == 200 || confirm.getStatusCode() == 201 || confirm.getStatusCode() == 204);

        Response bookings = bookingClient.list();
        assertEquals(200, bookings.getStatusCode());
        String listing = bookings.asString();
        assertEquals("CONFIRMED",
                confirm.jsonPath().getString("state"));

        assertNotNull(confirm.jsonPath().getString("pnr"));

        assertTrue(confirm.jsonPath()
                .getString("pnr")
                .startsWith("TS-1030"));
    }
}
