package tests;

import API_FrameWork.config.TestData;
import API_FrameWork.models.FlightResponse;
import API_FrameWork.models.SeatMapResponse;
import API_FrameWork.service.FlightService;
import API_FrameWork.service.SeatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SeatMapTest extends BaseTest {

    @Test
    @DisplayName("Verify seat map")
    void getSeatMap() {
        FlightService flightService = new FlightService();
        FlightResponse flights = flightService.searchFlights(
                TestData.FROM,
                TestData.TO,
                TestData.DATE,
                TestData.TRAVEL_CLASS
        );

        String flightId = flights.getFlights().get(0).getId();

        SeatService seatService = new SeatService();
        SeatMapResponse seatMap = seatService.getSeatMap(flightId);

        String availableSeat = findAvailableSeat(seatMap);

        Assertions.assertFalse(seatMap.getRows().isEmpty(), "Seat map should contain rows");
        Assertions.assertNotNull(availableSeat, "At least one seat should be available");

        System.out.println("Flight ID: " + flightId);
        System.out.println("Seat ID: " + availableSeat);
    }

    private String findAvailableSeat(SeatMapResponse seatMap) {
        for (SeatMapResponse.Row row : seatMap.getRows()) {
            for (SeatMapResponse.Seat seat : row.getSeats()) {
                if (!seat.isOccupied()) {
                    return seat.getSeatId();
                }
            }
        }
        return null;
    }
}