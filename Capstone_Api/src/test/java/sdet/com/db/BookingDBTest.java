package sdet.com.db;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import sdet.com.models.BookingResponse;
import sdet.com.services.TripStackApiService;
import sdet.com.support.TripStackConfig;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingDBTest {

    TripStackApiService api = new TripStackApiService();

    private String login() {

        return api.login(
                TripStackConfig.BASE_URL,
                TripStackConfig.LOGIN_EMAIL,
                TripStackConfig.LOGIN_PASSWORD
        );
    }

    private BookingResponse createConfirmedBooking(String token) {

        Response buses = api.searchBuses(
                TripStackConfig.BASE_URL,
                token,
                TripStackConfig.FROM,
                TripStackConfig.TO,
                TripStackConfig.JOURNEY_DATE
        );

        String busId = buses.jsonPath()
                .getString("buses.find { it.id=='BUS-DELIXC-01' }.id");

        Response seats = api.getBusSeats(
                TripStackConfig.BASE_URL,
                token,
                busId
        );

        String seat = api.firstAvailableSeat(seats);

        BookingResponse booking = api.holdBusBooking(
                TripStackConfig.BASE_URL,
                token,
                busId,
                seat
        );

        api.payBooking(
                TripStackConfig.BASE_URL,
                token,
                booking.getId()
        );

        return api.confirmBooking(
                TripStackConfig.BASE_URL,
                token,
                booking.getId()
        );
    }

    @Test
    void shouldStoreBookingIntoMySql() throws Exception {

        String token = login();

        BookingResponse confirmed =
                createConfirmedBooking(token);

        BookingDAO.saveBooking(
                confirmed.getId(),
                confirmed.getPnr(),
                TripStackConfig.LOGIN_EMAIL,
                confirmed.getState()
        );

        assertThat(
                BookingDAO.bookingExists(
                        confirmed.getPnr()
                )
        ).isTrue();
    }

    @Test
    void shouldSaveBookingId() throws Exception {

        String token = login();

        BookingResponse confirmed =
                createConfirmedBooking(token);

        BookingDAO.saveBooking(
                confirmed.getId(),
                confirmed.getPnr(),
                TripStackConfig.LOGIN_EMAIL,
                confirmed.getState()
        );

        String bookingId =
                BookingDAO.getBookingId(confirmed.getPnr());

        assertThat(bookingId)
                .isEqualTo(confirmed.getId());
    }

    @Test
    void shouldSavePassengerName() throws Exception {

        String token = login();

        BookingResponse confirmed =
                createConfirmedBooking(token);

        BookingDAO.saveBooking(
                confirmed.getId(),
                confirmed.getPnr(),
                TripStackConfig.LOGIN_EMAIL,
                confirmed.getState()
        );

        String passenger =
                BookingDAO.getPassengerName(
                        confirmed.getPnr());

        assertThat(passenger)
                .isEqualTo(
                        TripStackConfig.LOGIN_EMAIL
                );
    }

    @Test
    void shouldSaveConfirmedStatus() throws Exception {

        String token = login();

        BookingResponse confirmed =
                createConfirmedBooking(token);

        BookingDAO.saveBooking(
                confirmed.getId(),
                confirmed.getPnr(),
                TripStackConfig.LOGIN_EMAIL,
                confirmed.getState()
        );

        String status =
                BookingDAO.getStatus(
                        confirmed.getPnr());

        assertThat(status)
                .isEqualTo("CONFIRMED");
    }

    @Test
    void shouldInsertOneRecord() throws Exception {

        String token = login();

        BookingResponse confirmed =
                createConfirmedBooking(token);

        BookingDAO.saveBooking(
                confirmed.getId(),
                confirmed.getPnr(),
                TripStackConfig.LOGIN_EMAIL,
                confirmed.getState()
        );

        int count =
                BookingDAO.countBookings(
                        confirmed.getPnr());

        assertThat(count).isEqualTo(1);
    }

    @Test
    void shouldReturnTrueWhenBookingExists() throws Exception {

        String token = login();

        BookingResponse confirmed =
                createConfirmedBooking(token);

        BookingDAO.saveBooking(
                confirmed.getId(),
                confirmed.getPnr(),
                TripStackConfig.LOGIN_EMAIL,
                confirmed.getState()
        );

        assertThat(
                BookingDAO.bookingExists(
                        confirmed.getPnr()
                )
        ).isTrue();
    }
}