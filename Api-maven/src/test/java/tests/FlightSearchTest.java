package tests;

import API_FrameWork.config.TestData;
import API_FrameWork.models.FlightResponse;
import API_FrameWork.service.FlightService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FlightSearchTest extends BaseTest {

    @Test
    @DisplayName("Verify flight search")
    void searchFlights() {
        FlightService flightService = new FlightService();

        FlightResponse response = flightService.searchFlights(
                TestData.FROM,
                TestData.TO,
                TestData.DATE,
                TestData.TRAVEL_CLASS
        );

        FlightResponse.Flight flight = response.getFlights().get(0);

        Assertions.assertTrue(response.getCount() > 0, "At least one flight should be returned");
        Assertions.assertEquals(TestData.FROM, flight.getOrigin());
        Assertions.assertEquals(TestData.TO, flight.getDest());

        System.out.println("Flight ID: " + flight.getId());
    }
}