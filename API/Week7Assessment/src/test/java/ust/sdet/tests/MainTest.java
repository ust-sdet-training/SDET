package ust.sdet.tests;

import io.cucumber.java.eo.Se;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import ust.sdet.Data.TestDataBuilder;
import ust.sdet.SpecFactory.ConfigSpec;
import ust.sdet.Util.Functions;

@Epic("Flight Management")
@Feature("Seat Booking API")
public class MainTest {

    ConfigSpec configSpec = new ConfigSpec();

    TestDataBuilder testDataBuilder = new TestDataBuilder();

    Functions utilFunctions = new Functions();


    @Test
    @Story("Search for available flight seats")
    @Description("Validates that a user can successfully search for flights and retrieve available seat IDs.")
    @Severity(SeverityLevel.NORMAL)
    void SearchFlightSeats(){
        String token = utilFunctions.getToken();

        Response response = utilFunctions.searchFlight();

        String flightid = response.path("flights[0].id");

        String seatid =  utilFunctions.getFlightSeats(flightid);

        System.out.println(seatid);
    }

    @Test
    @Story("End-to-End Seat Booking Flow")
    @Description("Validates the complete flow: search, book, pay, confirm, and cancel seats.")
    @Severity(SeverityLevel.BLOCKER)
    void BookFlightSeats(){

        Response response = utilFunctions.searchFlight();

        String flightid = response.path("flights[0].id");

        String seatid =  utilFunctions.getFlightSeats(flightid);

        String token = utilFunctions.getToken();

        String bookingid = utilFunctions.bookSeats(token,flightid,seatid);

        response = utilFunctions.payForSeats(token,bookingid);

        response = utilFunctions.confirmSeats(token,bookingid);

        response = utilFunctions.cancelSeats(token,bookingid);

        System.out.println(response.asPrettyString());
    }

}
