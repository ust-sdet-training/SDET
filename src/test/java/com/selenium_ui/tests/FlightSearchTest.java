package com.selenium_ui.tests;

import com.selenium_ui.pages.HomePage;
import com.selenium_ui.pages.SearchResultPage;
import com.selenium_ui.pages.components.FlightCard;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FlightSearchTest extends BaseTest {

    @Test
    void verifyHappyPath() {
        SearchResultPage resultPage = new HomePage(driver).searchFlight("Chennai", "Bangalore");

        List<FlightCard> flights = resultPage.flights();
        assertFalse(flights.isEmpty());
        FlightCard flight = flights.getFirst();

        System.out.println("Airline : " + flight.airline());
        System.out.println("Departure : " + flight.departure());
        System.out.println("Arrival : " + flight.arrival());
        System.out.println("Duration : " + flight.duration());
        System.out.println("Fare : " + flight.fare());

        assertTrue(flight.fare() > 0);
    }
}