package tests;


import clients.AuthClient;
import clients.FlightClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class FlightTest {


    AuthClient authClient =
            new AuthClient();


    FlightClient flightClient =
            new FlightClient();



    @Test
    void flightTest(){


        // Login and get token
        String token =
                authClient.login();



        // Search flights
        Response response =
                flightClient.searchFlights(token);

        assertEquals(
                200,
                response.statusCode()
        );

    }

}