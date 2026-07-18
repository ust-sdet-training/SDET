package com.apitesting.tests;

import com.apitesting.config.Config;
import com.apitesting.support.Report;
import com.apitesting.support.builders.ApiSpecBuilders;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PerformanceTest {


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
    void paymentLatencyTest() {

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


        long startTime = System.currentTimeMillis();
        Response payResponse =
                given()
                        .spec(ApiSpecBuilders.reqSpecget(token))
                        .pathParam("id",bookingId)
                        .when()
                        .post("/bookings/{id}/pay")
                        .then()
                        .extract()
                        .response();

        long endTime = System.currentTimeMillis();

        long responseTime = endTime - startTime;

        int status = payResponse.statusCode();

        System.out.println("PAYMENT RESPONSE TIME = " + responseTime + " ms");
        System.out.println("PAY STATUS = " + status);
        System.out.println("PAY RESPONSE = ");
        System.out.println(payResponse.asPrettyString());


        if(status == 200){

            System.out.println(
                    "Payment completed successfully");

        }
        else if(status == 402 || status == 502 || status == 503 || status == 504 || status == 409){

            Report.info(
                    "Expected Fault",
                    payResponse.asPrettyString());

            System.out.println(
                    "Expected payment fault encountered : "
                            + status);

        }
        else{

            throw new AssertionError(
                    "Unexpected Payment Response : "
                            + payResponse.asPrettyString());
        }

        if(status == 200 && responseTime > 3000){

            throw new AssertionError(
                    "Payment Latency Threshold Breached : "
                            + responseTime + " ms");
        }


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

