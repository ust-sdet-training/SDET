package sdet.com.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import sdet.com.models.BookingResponse;
import sdet.com.services.TripStackApiService;
import sdet.com.support.TripStackConfig;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class TripStackSearchPerformanceTest {

    TripStackApiService api = new TripStackApiService();

    @Test
    void shouldReturnBusResultsWithinTwoSeconds() {

        String token = api.login(
                TripStackConfig.BASE_URL,
                TripStackConfig.LOGIN_EMAIL,
                TripStackConfig.LOGIN_PASSWORD);

        long start = System.currentTimeMillis();

        Response response = api.searchBuses(
                TripStackConfig.BASE_URL,
                token,
                TripStackConfig.FROM,
                TripStackConfig.TO,
                TripStackConfig.JOURNEY_DATE);

        long end = System.currentTimeMillis();

        long responseTime = end - start;

        assertThat(response.statusCode()).isEqualTo(200);

        assertThat(response.jsonPath().getList("buses"))
                .isNotEmpty();

        assertThat(responseTime)
                .isLessThan(2000);

        System.out.println("Response Time : " + responseTime + " ms");
    }
    @Test
    void shouldValidateSearchResults() {

        String token =
                api.login(
                        TripStackConfig.BASE_URL,
                        TripStackConfig.LOGIN_EMAIL,
                        TripStackConfig.LOGIN_PASSWORD);

        Response response =
                api.searchBuses(
                        TripStackConfig.BASE_URL,
                        token,
                        TripStackConfig.FROM,
                        TripStackConfig.TO,
                        TripStackConfig.JOURNEY_DATE);

        response.prettyPrint();

        assertThat(response.statusCode())
                .isEqualTo(200);


        assertThat(response.jsonPath().getString("from"))
                .isEqualTo("DEL");

        assertThat(response.jsonPath().getString("to"))
                .isEqualTo("IXC");

        assertThat(response.jsonPath().getInt("count"))
                .isGreaterThan(0);


        assertThat(response.jsonPath().getList("buses"))
                .isNotEmpty();


        assertThat(response.jsonPath().getString("buses[0].id"))
                .isNotBlank();

        assertThat(response.jsonPath().getString("buses[0].origin"))
                .isEqualTo("DEL");

        assertThat(response.jsonPath().getString("buses[0].dest"))
                .isEqualTo("IXC");

        assertThat(response.jsonPath().getString("buses[0].operatorName"))
                .isNotBlank();

        assertThat(response.jsonPath().getString("buses[0].kind"))
                .isNotBlank();

        assertThat(response.jsonPath().getInt("buses[0].farePaise"))
                .isGreaterThan(0);

        assertThat(response.jsonPath().getInt("buses[0].seatsLeft"))
                .isGreaterThan(0);
    }
    @Test
    void shouldRejectInvalidBookingId() {

        String token = api.login(
                TripStackConfig.BASE_URL,
                TripStackConfig.LOGIN_EMAIL,
                TripStackConfig.LOGIN_PASSWORD);

        Response response =
                given()
                        .baseUri(TripStackConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .post("/api/bookings/INVALID123/payment");

        assertThat(response.statusCode())
                .isIn(400, 415);
    }

    @Test
    void shouldHandlePaymentServerFailure() {

        String token = api.login(
                TripStackConfig.BASE_URL,
                TripStackConfig.LOGIN_EMAIL,
                TripStackConfig.LOGIN_PASSWORD);

        BookingResponse booking =
                api.holdBusBooking(
                        TripStackConfig.BASE_URL,
                        token,
                        "BUS-DELIXC-01",
                        "L3");

        Response response =
                given()
                        .baseUri(TripStackConfig.BASE_URL)
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .post("/api/bookings/" + booking.getId() + "/payment");

        assertThat(response.statusCode()).isEqualTo(500);
    }
}