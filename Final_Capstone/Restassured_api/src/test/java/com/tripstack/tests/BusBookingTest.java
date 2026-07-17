package com.tripstack.tests;


import org.junit.jupiter.api.*;

import com.tripstack.clients.*;



public class BusBookingTest {


    @Test

    void completeBusFlow(){


        BusClient bus=new BusClient();

        BookingClient booking=new BookingClient();



        String busId=
                bus.searchBus();


        bus.seatMap(busId);



        String id=
                booking.createBooking(busId);



        booking.paymentDecline(id);


    }



}