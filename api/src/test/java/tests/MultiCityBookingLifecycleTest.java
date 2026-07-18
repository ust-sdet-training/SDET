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



    private static final int MAX_RETRIES = 10;




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





    private BookingResponse createBookingWithRetry(
            String token,
            String inventoryId
    ){


        int attempt = 0;



        while(attempt < MAX_RETRIES){


            attempt++;


            String seat =
                    SeatGenerator.generateUniqueSeat();



            BookingRequest request =

                    BookingRequestBuilder.booking()

                            .journeyType("flight")

                            .inventoryId(
                                    inventoryId
                            )

                            .seatIds(
                                    List.of(seat)
                            )

                            .refundable(
                                    TestData.refundable()
                            )

                            .build();



            try {


                return bookingClient.createBooking(
                        token,
                        request
                );


            }

            catch(Exception e){


                if(
                        e.getMessage()
                                .contains("409")
                ){


                    System.out.println(
                            "Seat conflict detected. Generating new seat..."
                    );


                    continue;

                }



                throw e;

            }

        }



        throw new RuntimeException(
                "Unable to create booking after retries"
        );

    }






    @Test
    void multiCityBookingJourney(){



        String token =
                TestContext.getToken();





        // FIRST CITY BOOKING


        BookingResponse firstBooking =

                createBookingWithRetry(
                        token,
                        TestData.firstInventory()
                );



        TestContext.setFirstBookingId(
                firstBooking.id()
        );







        // SECOND CITY BOOKING


        BookingResponse secondBooking =

                createBookingWithRetry(
                        token,
                        TestData.secondInventory()
                );



        TestContext.setSecondBookingId(
                secondBooking.id()
        );






        // PAYMENT


        bookingClient.payBooking(
                token,
                firstBooking.id()
        );



        bookingClient.payBooking(
                token,
                secondBooking.id()
        );






        // CONFIRM


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







        // VALIDATION


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
                "Multi city booking journey completed successfully"
        );


    }

}