package com.tripstack.base;

import com.tripstack.client.AuthClient;
import com.tripstack.client.BookingClient;
import com.tripstack.client.BusClient;
import com.tripstack.client.FlightClient;
import com.tripstack.client.OpsClient;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    protected AuthClient authClient;
    protected FlightClient flightClient;
    protected BusClient busClient;
    protected BookingClient bookingClient;
    protected OpsClient opsClient;

    @BeforeEach
    public void setup() {

        authClient = new AuthClient();
        flightClient = new FlightClient();
        busClient = new BusClient();
        bookingClient = new BookingClient();
        opsClient = new OpsClient();
    }
}