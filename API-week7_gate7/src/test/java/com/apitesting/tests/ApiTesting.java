package com.apitesting.tests;

import com.apitesting.config.Config;
import com.apitesting.support.Report;
import com.apitesting.support.builders.ApiSpecBuilders;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.BeforeAll;
import com.apitesting.support.DbSetup;
public class ApiTesting {

    @BeforeAll
    static void setupDb() {

        DbSetup.createDatabase();
        DbSetup.createBookingsTable();

    }

    private String createBookingAndGetId() {

        String token = tokenGenerate();

        String flightId =
                given()
                        .spec(ApiSpecBuilders.reqSpecget(token))
                        .when()
                        .get("/flights?from=DEL&to=BLR")
                        .then()
                        .extract()
                        .path("flights[0].id");

        String seatId =
                given()
                        .spec(ApiSpecBuilders.reqSpecget(token))
                        .when()
                        .get("/flights/" + flightId + "/seats")
                        .then()
                        .extract()
                        .path("rows[1].seats[3].seat_id");

        Response bookingResponse =
                given()
                        .spec(ApiSpecBuilders.reqSpecget(token))
                        .body(Map.of(
                                "journeyType", "flight",
                                "inventoryId", flightId,
                                "seatIds", java.util.List.of(seatId)
                        ))
                        .when()
                        .post("/bookings")
                        .then()
                        .extract()
                        .response();

        System.out.println(bookingResponse.asPrettyString());

        return bookingResponse.path("id");
    }

    public static String tokenGenerate() {

        Report.step("Generating auth token");

        Map<String, String> map = new LinkedHashMap<>();
        map.put("email", Config.email);
        map.put("password", Config.password);

        return given()
                .spec(ApiSpecBuilders.reqSpecpost())
                .body(map)
                .when()
                .post("/auth/login")
                .then()
                .spec(ApiSpecBuilders.resSpecpost())
                .extract()
                .path("token");

    }

    @Test
    @DisplayName("GET the using auth token")
    void getFlight() {

        Report.step("GetFlight");

        Response res =
                given()
                        .spec(ApiSpecBuilders.reqSpecget(tokenGenerate()))
                        .when()
                        .get("/flights?from=DEL&to=BLR")
                        .then()
                        .spec(ApiSpecBuilders.resSpecget())
                        .body("from", not(emptyString()))
                        .body("to", not(emptyString()))
                        .body("from", equalTo("DEL"))
                        .body("to", equalTo("BLR"))
                        .body("class", equalTo("economy"))
                        .body("count", equalTo(48))
                        .body("pax", equalTo(1))
                        .extract()
                        .response();


        Report.info("Response", res.asPrettyString());
        System.out.println(res.asPrettyString());

        DbSetup.insertFlightAudit(
                res.path("from"),
                res.path("to"),
                "1001",
                res.asPrettyString()
        );

    }

    @Test
    @DisplayName("GET the using auth token")
    void getBooking() {

        Report.step("Get Booking");

        Response res =
                given()
                        .spec(ApiSpecBuilders.reqSpecget(tokenGenerate()))
                        .when()
                        .get("/bookings")
                        .then()
                        .spec(ApiSpecBuilders.resSpecget())
                        .body("empId[0]", not(emptyString()))
                        .body("empId[0]", equalTo("1001"))
                        .body("journeyType[0]", equalTo("flight"))
                        .extract()
                        .response();


        Report.info("Response", res.asPrettyString());
    }

    @Test
    @DisplayName("Get Current User Details")
    void getCurrentUser() {

        Report.step("Get Current User");

        Response res =
                given()
                        .spec(ApiSpecBuilders.reqSpecget(tokenGenerate()))
                        .when()
                        .get("/auth/me")
                        .then()
                        .statusCode(200)
                        .body("empId", equalTo("1001"))
                        .body("email", equalTo(Config.email))
                        .extract()
                        .response();

        Report.info("Response", res.asPrettyString());

    }


    @Test
    @DisplayName("Get Flight Seats")
    void getFlightSeats() {

        String token = tokenGenerate();

        Response flightResponse =
                given()
                        .spec(ApiSpecBuilders.reqSpecget(token))
                        .when()
                        .get("/flights?from=DEL&to=BLR")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        String flightId =
                flightResponse.path("flights[0].id");

        Report.info("Flight Id", flightId);

        Response seatResponse =
                given()
                        .spec(ApiSpecBuilders.reqSpecget(token))
                        .when()
                        .get("/flights/" + flightId + "/seats")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        Report.info(
                "Seat Response",
                seatResponse.asPrettyString()
        );

    }


//    @Test
//    @DisplayName("End To End Flight Booking")
//    void bookFlightSeats() {
//
//        String token = tokenGenerate();
//
//        Response flightResponse =
//                given()
//                        .spec(ApiSpecBuilders.reqSpecget(token))
//                        .when()
//                        .get("/flights?from=DEL&to=BLR")
//                        .then()
//                        .statusCode(200)
//                        .extract()
//                        .response();
//
//        String flightId =
//                flightResponse.path("flights[0].id");
//
//        Report.info("Flight Id", flightId);
//
//        Response seatResponse =
//                given()
//                        .spec(ApiSpecBuilders.reqSpecget(token))
//                        .when()
//                        .get("/flights/" + flightId + "/seats")
//                        .then()
//                        .statusCode(200)
//                        .extract()
//                        .response();
//
//        Report.info(
//                "Seat Response",
//                seatResponse.asPrettyString()
//        );
//
//        Report.info("Seat Response",
//                seatResponse.asPrettyString());
//
//        String seatId = seatResponse.path("rows[1].seats[3].seat_id");
//        Report.info("Seat Id", seatId);
//
//        Response bookingResponse =
//                given()
//                        .spec(ApiSpecBuilders.reqSpecget(token))
//                        .body(Map.of(
//                                "journeyType", "flight",
//                                "inventoryId", flightId,
//                                "seatIds", java.util.List.of(seatId)
//                        ))
//                        .when()
//                        .post("/bookings")
//                        .then()
//                        .extract()
//                        .response();
//
//        System.out.println(bookingResponse.asPrettyString());
//
//        String bookingId =
//                bookingResponse.path("id");
//
//
//        Response payResponse =
//                given()
//                        .spec(ApiSpecBuilders.reqSpecget(token))
//                        .pathParam("id", bookingId)
//                        .when()
//                        .post("/bookings/{id}/pay")
//                        .then()
//                        .statusCode(200)
//                        .extract()
//                        .response();
//
//        Report.info(
//                "Pay Response",
//                payResponse.asPrettyString()
//        );
//
//
//        Response confirmResponse =
//                given()
//                        .spec(ApiSpecBuilders.reqSpecget(token))
//                        .pathParam("id", bookingId)
//                        .when()
//                        .post("/bookings/{id}/confirm")
//                        .then()
//                        .statusCode(200)
//                        .extract()
//                        .response();
//
//        Report.info(
//                "Confirm Response",
//                confirmResponse.asPrettyString()
//        );
//
//        String pnr =
//                confirmResponse.path("pnr");
//
//        Report.info(
//                "PNR",
//                pnr
//        );
//
//        Response bookingByPnr =
//                given()
//                        .spec(ApiSpecBuilders.reqSpecget(token))
//                        .when()
//                        .get("/bookings/" + pnr)
//                        .then()
//                        .statusCode(200)
//                        .extract()
//                        .response();
//
//        Report.info(
//                "Booking By PNR",
//                bookingByPnr.asPrettyString()
//        );
//
//
//        Response cancelResponse =
//                given()
//                        .spec(ApiSpecBuilders.reqSpecget(token))
//                        .pathParam("id", bookingId)
//                        .when()
//                        .post("/bookings/{id}/cancel")
//                        .then()
//                        .statusCode(200)
//                        .extract()
//                        .response();
//
//        Report.info(
//                "Cancel Response",
//                cancelResponse.asPrettyString()
//        );
//
//    }
//
//
//    @Test
//    @DisplayName("Create Booking")
//    void createBooking() {
//
//        String bookingId = createBookingAndGetId();
//
//        Report.info(
//                "Booking Id",
//                String.valueOf(bookingId)
//        );
//    }
//
//
//    @Test
//    @DisplayName("Pay Booking")
//    void payBooking() {
//
//        String token = tokenGenerate();
//
//        String bookingId =
//                createBookingAndGetId();
//
//        Response response =
//                given()
//                        .spec(ApiSpecBuilders.reqSpecget(token))
//                        .pathParam("id", bookingId)
//                        .when()
//                        .post("/bookings/{id}/pay")
//                        .then()
//                        .statusCode(200)
//                        .extract()
//                        .response();
//
//        Report.info(
//                "Pay Response",
//                response.asPrettyString()
//        );
//    }
//
//
//    @Test
//    @DisplayName("Confirm Booking")
//    void confirmBooking() {
//
//        String token = tokenGenerate();
//
//        String bookingId =
//                createBookingAndGetId();
//
//        given()
//                .spec(ApiSpecBuilders.reqSpecget(token))
//                .pathParam("id", bookingId)
//                .when()
//                .post("/bookings/{id}/pay");
//
//        Response response =
//                given()
//                        .spec(ApiSpecBuilders.reqSpecget(token))
//                        .pathParam("id", bookingId)
//                        .when()
//                        .post("/bookings/{id}/confirm")
//                        .then()
//                        .statusCode(200)
//                        .extract()
//                        .response();
//
//        Report.info(
//                "Confirm Response",
//                response.asPrettyString()
//        );
//
//
//        @Test
//        @DisplayName("Get Booking By PNR")
//        void getBookingByPnr () {
//
//            String token = tokenGenerate();
//
//            String bookingId =
//                    createBookingAndGetId();
//
//            given()
//                    .spec(ApiSpecBuilders.reqSpecget(token))
//                    .pathParam("id", bookingId)
//                    .when()
//                    .post("/bookings/{id}/pay");
//
//            Response confirmResponse =
//                    given()
//                            .spec(ApiSpecBuilders.reqSpecget(token))
//                            .pathParam("id", bookingId)
//                            .when()
//                            .post("/bookings/{id}/confirm")
//                            .then()
//                            .extract()
//                            .response();
//
//            String pnr =
//                    confirmResponse.path("pnr");
//
//            Response response =
//                    given()
//                            .spec(ApiSpecBuilders.reqSpecget(token))
//                            .when()
//                            .get("/bookings/" + pnr)
//                            .then()
//                            .statusCode(200)
//                            .extract()
//                            .response();
//
//            Report.info(
//                    "Booking By PNR",
//                    response.asPrettyString()
//            );
//        }
//
//        @Test
//        @DisplayName("Cancel Booking")
//        void cancelBooking () {
//
//            String token = tokenGenerate();
//
//            String bId =
//                    createBookingAndGetId();
//
//            given()
//                    .spec(ApiSpecBuilders.reqSpecget(token))
//                    .pathParam("id", bId)
//                    .when()
//                    .post("/bookings/{id}/pay");
//
//            given()
//                    .spec(ApiSpecBuilders.reqSpecget(token))
//                    .pathParam("id", bId)
//                    .when()
//                    .post("/bookings/{id}/confirm");
//
//            Response res =
//                    given()
//                            .spec(ApiSpecBuilders.reqSpecget(token))
//                            .pathParam("id", bId)
//                            .when()
//                            .post("/bookings/{id}/cancel")
//                            .then()
//                            .statusCode(200)
//                            .extract()
//                            .response();
//
//            Report.info(
//                    "Cancel Response",
//                    res.asPrettyString()
//            );
//        }
//    }
}


