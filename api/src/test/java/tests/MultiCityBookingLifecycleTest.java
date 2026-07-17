package tests;


import builders.BookingRequestBuilder;
import clients.AuthClient;
import clients.BookingClient;

import models.BookingRequest;
import models.BookingResponse;
import models.LoginResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import support.TestContext;
import support.TestData;
import utils.SeatGenerator;


import java.util.List;



public class MultiCityBookingLifecycleTest {



    static AuthClient authClient =
            new AuthClient();


    static BookingClient bookingClient =
            new BookingClient();





    @BeforeAll
    static void login(){


        LoginResponse response =
                authClient.login(
                        TestData.loginUser()
                );


        TestContext.setToken(
                response.token()
        );


    }





    @Test
    void multiCityBookingJourney(){



        String token =
                TestContext.getToken();



        String seat1 =
                SeatGenerator.generateUniqueSeat();


        String seat2 =
                SeatGenerator.generateUniqueSeat();




        BookingRequest firstRequest =

                BookingRequestBuilder.booking()

                        .journeyType("flight")

                        .inventoryId(
                                TestData.firstInventory()
                        )

                        .seatIds(
                                List.of(seat1)
                        )

                        .refundable(
                                TestData.refundable()
                        )

                        .build();





        BookingResponse firstBooking =

                bookingClient.createBooking(
                        token,
                        firstRequest
                );



        TestContext.setFirstBookingId(
                firstBooking.id()
        );




        BookingRequest secondRequest =

                BookingRequestBuilder.booking()

                        .journeyType("flight")

                        .inventoryId(
                                TestData.secondInventory()
                        )

                        .seatIds(
                                List.of(seat2)
                        )

                        .refundable(
                                TestData.refundable()
                        )

                        .build();





        BookingResponse secondBooking =

                bookingClient.createBooking(
                        token,
                        secondRequest
                );



        TestContext.setSecondBookingId(
                secondBooking.id()
        );





        bookingClient.payBooking(
                token,
                firstBooking.id()
        );



        bookingClient.payBooking(
                token,
                secondBooking.id()
        );





        BookingResponse firstConfirmed =

                bookingClient.confirmBooking(
                        token,
                        firstBooking.id()
                );



        BookingResponse secondConfirmed =

                bookingClient.confirmBooking(
                        token,
                        secondBooking.id()
                );




        TestContext.setFirstPnr(
                firstConfirmed.pnr()
        );


        TestContext.setSecondPnr(
                secondConfirmed.pnr()
        );





        BookingResponse booking1 =

                bookingClient.getBooking(
                        token,
                        firstConfirmed.pnr()
                );


        BookingResponse booking2 =

                bookingClient.getBooking(
                        token,
                        secondConfirmed.pnr()
                );




        Assertions.assertEquals(
                "CONFIRMED",
                booking1.state()
        );


        Assertions.assertEquals(
                "CONFIRMED",
                booking2.state()
        );



        Assertions.assertTrue(
                firstConfirmed.pnr()
                        .matches(
                                "TS-1015-\\d{4}"
                        )
        );



        Assertions.assertTrue(
                secondConfirmed.pnr()
                        .matches(
                                "TS-1015-\\d{4}"
                        )
        );


        System.out.println(
                "Multi city booking journey completed"
        );

    }

}