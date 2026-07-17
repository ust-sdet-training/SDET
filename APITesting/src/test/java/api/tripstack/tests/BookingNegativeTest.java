package api.tripstack.tests;

import api.tripstack.base.BaseApiTest;
import api.tripstack.clients.BookingClient;
import api.tripstack.models.BookingRequest;
import api.tripstack.models.LoginRequest;
import api.tripstack.utils.DateUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookingNegativeTest extends BaseApiTest {

    @Test
    void rejectsInvalidLogin() {
        Response response = authClient.login(new LoginRequest("invalid@tripstack.test", "wrong-password"));
        assertEquals(401, response.getStatusCode());
    }

    @Test
    void rejectsConfirmationBeforePayment() {
        String token = loginUser();
        BookingClient bookingClient = new BookingClient(token);
        BookingRequest holdRequest = createHoldRequest();

        Response hold = bookingClient.createHold(holdRequest);
        assertEquals(201, hold.getStatusCode());
        String bookingId = hold.jsonPath().getString("bookingId");

        Response confirm = bookingClient.confirm(bookingId);
        assertTrue(confirm.getStatusCode() == 400 || confirm.getStatusCode() == 404);
    }

    @Test
    void rejectsMissingBearerToken() {
        Response response = given().get("/api/bookings");
        assertEquals(401, response.getStatusCode());
    }

    @Test
    void rejectsAnEmptySeatHold() {
        String token = loginUser();
        BookingClient bookingClient = new BookingClient(token);
        BookingRequest invalidRequest = new BookingRequest("bus", "BUS-HYDBOM-01", List.of(), true, 300);

        Response response = bookingClient.createHold(invalidRequest);
        assertEquals(400, response.getStatusCode());
    }

    private BookingRequest createHoldRequest() {
        Response buses = busClient.search("HYD", "BOM", DateUtils.indiaDateAfter(14));
        String busId = busClient.firstAvailableAcSemiBus(buses);
        Response seats = busClient.seatMap(busId);
        String seatId = busClient.firstAvailableSeat(seats);
        return new BookingRequest("bus", busId, List.of(seatId), true, 300);
    }
}
