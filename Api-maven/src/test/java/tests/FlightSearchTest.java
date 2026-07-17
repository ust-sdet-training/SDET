package tests;

import API_FrameWork.models.FlightResponse;
import API_FrameWork.service.FlightService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FlightSearchTest {

    @Test
    void searchFlights() {

        FlightService service = new FlightService();

        FlightResponse response =
                service.searchFlights(
                        "JAI",
                        "BOM",
                        "2026-08-14",
                        "economy");

        Assertions.assertTrue(response.getCount() > 0);

        FlightResponse.Flight flight =
                response.getFlights().get(0);

        Assertions.assertEquals("JAI", flight.getOrigin());
        Assertions.assertEquals("BOM", flight.getDest());

        System.out.println("Flight ID : " + flight.getId());
    }
}