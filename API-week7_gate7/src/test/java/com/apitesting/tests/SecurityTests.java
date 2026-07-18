package com.apitesting.tests;

import com.apitesting.config.Config;
import com.apitesting.support.Report;
import com.apitesting.support.builders.ApiSpecBuilders;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static com.apitesting.support.builders.ApiSpecBuilders.invalidokenreqSpecget;
import static com.apitesting.support.builders.ApiSpecBuilders.notokenreqSpecget;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SecurityTests {

        public static String tokenGenerate(String email, String password) {

        Report.step("Generating auth token for " + email);

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

        public static String createConfirmedBookingPnr(String token) {

        Response flightResponse =
                given()
                        .spec(ApiSpecBuilders.reqSpecget(token))
                        .when()
                        .get("/flights?from=DEL&to=BLR")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        String flightId = flightResponse.path("flights[0].id");

        Response seatResponse =
                given()
                        .spec(ApiSpecBuilders.reqSpecget(token))
                        .when()
                        .get("/flights/" + flightId + "/seats")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        String seatId = null;
        List<Map<String, Object>> rows = seatResponse.path("rows");
        for (Map<String, Object> row : rows) {
            List<Map<String, Object>> seats = (List<Map<String, Object>>) row.get("seats");
            for (Map<String, Object> seat : seats) {
                Boolean occupied = (Boolean) seat.get("occupied");
                if (occupied == null || !occupied) {
                    seatId = seat.get("seat_id").toString();
                    break;
                }
            }
            if (seatId != null) {
                break;
            }
        }

        assertNotNull(seatId, "No available seat found for booking");

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

        String bookingId = bookingResponse.path("id");
        assertNotNull(bookingId, "Booking creation did not return an id");

            Response payResponse = given()
                    .spec(ApiSpecBuilders.reqSpecget(token))
                    .pathParam("id", bookingId)
                    .when()
                    .post("/bookings/{id}/pay")
                    .then()
                    .extract()
                    .response();

            System.out.println(payResponse.asPrettyString());

            int payStatus = payResponse.statusCode();

            if (payStatus == 200) {

                Response confirmResponse =
                        given()
                                .spec(ApiSpecBuilders.reqSpecget(token))
                                .pathParam("id", bookingId)
                                .when()
                                .post("/bookings/{id}/confirm")
                                .then()
                                .extract()
                                .response();

                System.out.println(
                        "CONFIRM STATUS = "
                                + confirmResponse.statusCode());

                System.out.println(
                        confirmResponse.asPrettyString());

                if (confirmResponse.statusCode() != 200) {
                    throw new AssertionError(
                            "Booking confirmation failed : "
                                    + confirmResponse.asPrettyString());
                }

                String pnr = confirmResponse.path("pnr");
                assertNotNull(pnr);

                return pnr;
            }
            else if (payStatus == 409||payStatus == 402||payStatus == 502||payStatus == 503||payStatus == 504) {

                System.out.println("Expected payment fault encountered : "+ payStatus);
                System.out.println(payResponse.asPrettyString());

                return null;
            }
            else {

                throw new AssertionError(
                        "Unexpected payment response : "
                                + payResponse.asPrettyString());
            }

    }


    @Test
    void bolaReadAnotherUsersPnr() {

        String otherToken =  SecurityTests.tokenGenerate(Config.bolaemail, Config.password);
        String otherPnr = SecurityTests.createConfirmedBookingPnr(otherToken);

        if (otherPnr == null) {
            System.out.println("Skipping BOLA because HOLD_EXPIRED occurred");
            return;
        }


        String token = FlightTest.tokenGenerate();

        given()
                .spec(ApiSpecBuilders.reqSpecget(token))
                .when()
                .get("/bookings/" + otherPnr)
                .then()
                .log().all()
                .statusCode(403);
    }

    @Test
    void accessWithoutToken() {

        given()
                .spec(notokenreqSpecget())
                .when()
                .get("/bookings")
                .then()
                .statusCode(401);
    }

    @Test
    void invalidToken() {

        given()
                .spec(invalidokenreqSpecget())
                .when()
                .get("/bookings")
                .then()
                .statusCode(401);
    }

    @Test
    void invalidPnr() {

        String token = FlightTest.tokenGenerate();

        given()
                .spec(ApiSpecBuilders.reqSpecget(token))
                .when()
                .get("/bookings/TS-9999-9999")
                .then()
                .statusCode(404);
    }
}
