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
        FlightResponse response = new FlightService().searchFlights(
                TestData.FROM,
                TestData.TO,
                TestData.DATE,
                TestData.TRAVEL_CLASS
        );
        Assertions.assertFalse(response.flights().isEmpty(), "At least one flight should be returned");
        FlightResponse.Flight flight = response.flights().getFirst();
        Assertions.assertEquals(TestData.FROM, flight.origin());
        Assertions.assertEquals(TestData.TO, flight.dest());
        System.out.println("Flight ID: " + flight.id());
    }
}
