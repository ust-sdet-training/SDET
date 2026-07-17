package sdet.com.services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import sdet.com.models.AuthTokenResponse;
import sdet.com.models.BookingResponse;
import sdet.com.specs.TripStackRequestSpecs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TripStackApiService {
    private Response lastResponse;
    public Response getLastResponse() {
        return lastResponse;
    }

    public String login(String baseUrl,
                        String email,
                        String password) {

        lastResponse =
                given()
                        .baseUri(baseUrl)
                        .contentType(ContentType.JSON)
                        .body(Map.of(
                                "email", email,
                                "password", password
                        ))
                        .when()
                        .post("/api/auth/login");

        lastResponse.then().statusCode(200);

        return lastResponse.as(AuthTokenResponse.class).getToken();
    }

    public Response me(String baseUrl,
                       String token) {

        lastResponse =
                given()
                        .spec(TripStackRequestSpecs.authSpec(baseUrl, token))
                        .when()
                        .get("/api/auth/me");

        lastResponse.then().statusCode(200);

        return lastResponse;
    }

    public Response searchBuses(String baseUrl,
                                String token,
                                String from,
                                String to,
                                String date) {

        lastResponse =
                given()
                        .spec(TripStackRequestSpecs.authSpec(baseUrl, token))
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .queryParam("date", date)
                        .when()
                        .get("/api/buses");

        lastResponse.then().statusCode(200);

        return lastResponse;
    }

    public Response getBusSeats(String baseUrl,
                                String token,
                                String busId) {

        lastResponse =
                given()
                        .spec(TripStackRequestSpecs.authSpec(baseUrl, token))
                        .when()
                        .get("/api/buses/" + busId + "/seats");

        lastResponse.then().statusCode(200);

        return lastResponse;
    }

    public String firstAvailableSeat(Response response) {

        List<Map<String,Object>> lower =
                response.jsonPath().getList("decks.lower");

        for(Map<String,Object> seat : lower){

            if("available".equals(seat.get("state"))){

                return seat.get("seatId").toString();

            }

        }

        List<Map<String,Object>> upper =
                response.jsonPath().getList("decks.upper");

        for(Map<String,Object> seat : upper){

            if("available".equals(seat.get("state"))){

                return seat.get("seatId").toString();

            }

        }

        throw new RuntimeException("No available seat found");
    }

    public BookingResponse holdBusBooking(String baseUrl,
                                          String token,
                                          String busId,
                                          String seatId) {

        Map<String,Object> body = new HashMap<>();

        body.put("journeyType","bus");
        body.put("inventoryId",busId);
        body.put("seatIds", List.of(seatId));
        body.put("refundable",true);
        body.put("holdTtlSec",120);

        lastResponse =
                given()
                        .spec(TripStackRequestSpecs.jsonSpec(baseUrl, token))
                        .body(body)
                        .when()
                        .post("/api/bookings");

        lastResponse.then().statusCode(org.hamcrest.Matchers.anyOf(
                org.hamcrest.Matchers.is(200),
                org.hamcrest.Matchers.is(201)
        ));

        return lastResponse.as(BookingResponse.class);
    }

    public BookingResponse payBooking(String baseUrl,
                                      String token,
                                      String bookingId){

        lastResponse =
                given()
                        .spec(TripStackRequestSpecs.jsonSpec(baseUrl, token))
                        .body(new HashMap<>())
                        .when()
                        .post("/api/bookings/" + bookingId + "/pay");

        lastResponse.then().statusCode(200);

        return lastResponse.as(BookingResponse.class);
    }

    public BookingResponse confirmBooking(String baseUrl,
                                          String token,
                                          String bookingId){

        lastResponse =
                given()
                        .spec(TripStackRequestSpecs.jsonSpec(baseUrl, token))
                        .when()
                        .post("/api/bookings/" + bookingId + "/confirm");

        lastResponse.then().statusCode(200);

        return lastResponse.as(BookingResponse.class);
    }

    public Response listBookings(String baseUrl,
                                 String token){

        lastResponse =
                given()
                        .spec(TripStackRequestSpecs.authSpec(baseUrl, token))
                        .when()
                        .get("/api/bookings");

        lastResponse.then().statusCode(200);

        return lastResponse;
    }


}