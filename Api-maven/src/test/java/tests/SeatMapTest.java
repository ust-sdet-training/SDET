package tests;

import API_FrameWork.models.FlightResponse;
import API_FrameWork.models.SeatMapResponse;
import API_FrameWork.service.FlightService;
import API_FrameWork.service.SeatService;
import API_FrameWork.support.TestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SeatMapTest {

    @Test
    void getSeatMap() {

        FlightService flightService = new FlightService();

        FlightResponse flights =
                flightService.searchFlights(
                        "JAI",
                        "BOM",
                        "2026-08-14",
                        "economy");

        TestContext.flightId = flights.getFlights().get(0).getId();

        SeatService seatService = new SeatService();

        SeatMapResponse seatMap =
                seatService.getSeatMap(TestContext.flightId);

        Assertions.assertFalse(seatMap.getRows().isEmpty());

        // Find the first available seat
        outer:
        for (SeatMapResponse.Row row : seatMap.getRows()) {

            for (SeatMapResponse.Seat seat : row.getSeats()) {

                if (!seat.isOccupied()) {

                    TestContext.seatId = seat.getSeatId();

                    System.out.println("Flight ID : " + TestContext.flightId);
                    System.out.println("Seat ID   : " + TestContext.seatId);

                    break outer;
                }
            }
        }

        Assertions.assertNotNull(TestContext.seatId);
    }
}