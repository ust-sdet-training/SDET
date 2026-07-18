package com.apitesting.tests;

import com.apitesting.config.Config;
import com.apitesting.support.Report;
import com.apitesting.support.builders.ApiSpecBuilders;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
public class FlightTest {


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

    public static String tokenGenerate(String email, String password) {

        Map<String, String> map = new LinkedHashMap<>();

        map.put("email", email);
        map.put("password", password);

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

            Response res=
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
                            .body("class",equalTo("economy") )
                            .body("count",equalTo(48))
                            .body("pax", equalTo(1))
                    .extract()
                    .response();


            Report.info("Response", res.asPrettyString());
            System.out.println(res.asPrettyString());

        }

    @Test
    @DisplayName("GET the using auth token")
    void getBooking() {

        Report.step("Get Booking");

        Response res=
                given()
                        .spec(ApiSpecBuilders.reqSpecget(tokenGenerate()))
                        .when()
                        .get("/bookings")
                        .then()
                        .spec(ApiSpecBuilders.resSpecget())
                        .body("empId[0]",not(emptyString()))
                        .body("empId[0]",equalTo("1001"))
                        .body("journeyType[0]",equalTo("flight"))
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


    @Test
    @DisplayName("End To End Flight Booking")
    void bookFlightSeats() {

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

        Report.info("Seat Response",
                seatResponse.asPrettyString());



        String seatId = getAvailableSeatId(seatResponse);
        Report.info("Seat Id",seatId);

        Response bookingResponse =
                given()
                        .spec(ApiSpecBuilders.reqSpecget(token))
                        .body(Map.of(
                                "journeyType", "flight",
                                "inventoryId", flightId,
                                "seatIds", List.of(seatId)
                        ))
                        .when()
                        .post("/bookings")
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        System.out.println(bookingResponse.asPrettyString());

        String bookingId =
                bookingResponse.path("id");



        Response payResponse =
                given()
                        .spec(ApiSpecBuilders.reqSpecget(token))
                        .pathParam("id",bookingId)
                        .when()
                        .post("/bookings/{id}/pay")
                        .then()
                        .extract()
                        .response();

        int status = payResponse.statusCode();

        System.out.println("PAY STATUS = " + status);
        System.out.println("PAY RESPONSE = ");
        System.out.println(payResponse.asPrettyString());

        if(status == 200){
            System.out.println("ENTERING CONFIRM FLOW");

            Response confirmResponse =
                    given()
                            .spec(ApiSpecBuilders.reqSpecget(token))
                            .pathParam("id", bookingId)
                            .when()
                            .post("/bookings/{id}/confirm")
                            .then()
                            .statusCode(200)
                            .extract()
                            .response();

            System.out.println("CONFIRM STATUS = " + confirmResponse.statusCode());
            System.out.println(confirmResponse.asPrettyString());

            Report.info(
                    "Confirm Response",
                    confirmResponse.asPrettyString()
            );

            String pnr =
                    confirmResponse.path("pnr");

            Report.info(
                    "PNR",
                    pnr
            );

            Response bookingByPnr =
                    given()
                            .spec(ApiSpecBuilders.reqSpecget(token))
                            .when()
                            .get("/bookings/" + pnr)
                            .then()
                            .statusCode(200)
                            .extract()
                            .response();

            Report.info(
                    "Booking By PNR",
                    bookingByPnr.asPrettyString()
            );


            Response cancelResponse =
                    given()
                            .spec(ApiSpecBuilders.reqSpecget(token))
                            .pathParam("id", bookingId)
                            .when()
                            .post("/bookings/{id}/cancel")
                            .then()
                            .statusCode(200)
                            .extract()
                            .response();

            Report.info(
                    "Cancel Response",
                    cancelResponse.asPrettyString()
            );


            Report.info(
                    "Payment Response",
                    payResponse.asPrettyString());
        }
        else if(status == 504||status == 402||status == 503||status == 409||status == 502){
            Report.info(
                    "Expected Fault",
                    payResponse.asPrettyString());

            System.out.println("Expected payment fault encountered : "+ status);
        }
        else{
            throw new AssertionError(
                    "Unexpected Payment Response : "
                            + payResponse.asPrettyString());
        }

        Report.info(
                "Pay Response",
                payResponse.asPrettyString()
        );



    }

    private String getAvailableSeatId(Response seatResponse) {
        List<Map<String, Object>> rows = seatResponse.path("rows");

        for (Map<String, Object> row : rows) {
            List<Map<String, Object>> seats =
                    (List<Map<String, Object>>) row.get("seats");

            for (Map<String, Object> seat : seats) {
                Boolean occupied = (Boolean) seat.get("occupied");

                if (occupied == null || !occupied) {
                    return seat.get("seat_id").toString();
                }
            }
        }

        throw new IllegalStateException("No available seat found");
    }

    }


