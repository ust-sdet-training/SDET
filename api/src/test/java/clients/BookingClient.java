package clients;


import constants.ApiEndpoints;
import io.restassured.response.Response;
import models.BookingRequest;
import models.BookingResponse;
import utils.SeatGenerator;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class BookingClient extends BaseClient {


    // =========================
    // CREATE BOOKING
    // =========================

    public BookingResponse createBooking(
            String token,
            BookingRequest request
    ) {


        Response response;


        while (true) {


            response =

                    given()

                            .spec(
                                    authenticatedRequest(token)
                            )

                            .body(request)

                            .when()

                            .post(
                                    ApiEndpoints.BOOKINGS
                            );



            // Seat conflict handling
            if(response.statusCode() == 409) {


                System.out.println(
                        "Seat conflict detected. Generating new seat..."
                );


                request =

                        new BookingRequest(

                                request.journeyType(),

                                request.inventoryId(),

                                List.of(
                                        SeatGenerator.generateUniqueSeat()
                                ),

                                request.refundable()

                        );


                continue;

            }


            break;

        }



        return response

                .then()

                .log().all()

                .statusCode(201)

                .body(
                        "state",
                        equalTo("HELD")
                )

                .extract()

                .as(
                        BookingResponse.class
                );

    }





    // =========================
    // PAY BOOKING
    // =========================

    public BookingResponse payBooking(
            String token,
            String bookingId
    ) {


        Response response =


                given()

                        .spec(
                                authenticatedRequest(token)
                        )

                        .pathParam(
                                "bookingId",
                                bookingId
                        )


                        .when()

                        .post(
                                ApiEndpoints.PAY_BOOKING
                        )


                        .then()

                        .log().all()

                        .statusCode(200)

                        .body(
                                "state",
                                equalTo(
                                        "PAYMENT_PENDING"
                                )
                        )

                        .extract()
                        .response();



        return response.as(
                BookingResponse.class
        );

    }







    // =========================
    // CONFIRM BOOKING
    // =========================

    public BookingResponse confirmBooking(
            String token,
            String bookingId
    ) {


        Response response =


                given()

                        .spec(
                                authenticatedRequest(token)
                        )

                        .pathParam(
                                "bookingId",
                                bookingId
                        )


                        .when()

                        .post(
                                ApiEndpoints.CONFIRM_BOOKING
                        )


                        .then()

                        .log().all()

                        .statusCode(200)

                        .body(
                                "state",
                                equalTo(
                                        "CONFIRMED"
                                )
                        )

                        .extract()
                        .response();



        return response.as(
                BookingResponse.class
        );

    }








    // =========================
    // GET BOOKING
    // =========================

    public BookingResponse getBooking(
            String token,
            String pnr
    ) {


        Response response =


                given()

                        .spec(
                                authenticatedRequest(token)
                        )

                        .pathParam(
                                "pnr",
                                pnr
                        )


                        .when()

                        .get(
                                ApiEndpoints.GET_BOOKING
                        )


                        .then()

                        .log().all()

                        .statusCode(200)

                        .body(
                                "pnr",
                                equalTo(pnr)
                        )

                        .body(
                                "state",
                                equalTo(
                                        "CONFIRMED"
                                )
                        )

                        .extract()
                        .response();



        return response.as(
                BookingResponse.class
        );

    }

}