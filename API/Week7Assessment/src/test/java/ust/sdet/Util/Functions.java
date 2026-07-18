package ust.sdet.Util;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ust.sdet.Data.TestDataBuilder;
import ust.sdet.SpecFactory.AuthSpec;
import ust.sdet.SpecFactory.ConfigSpec;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Functions {

    ConfigSpec configSpec = new ConfigSpec();

    TestDataBuilder testDataBuilder = new TestDataBuilder();

    AuthSpec authSpec = new AuthSpec();

    @Step("Generate Authentication Token")
    public String getToken(){
        return given()
                .spec(configSpec.setHeaders())
                .body(testDataBuilder.buildLoginPayload("Carol"))
                .when()
                .post("/auth/login/")
                .then()
                .extract()
                .response()
                .path("token");
    }

    public String getTokenUser(String user){
        return given()
                .spec(configSpec.setHeaders())
                .body(testDataBuilder.buildLoginPayload(user))
                .when()
                .post("/auth/login/")
                .then()
                .extract()
                .response()
                .path("token");
    }

    @Step("Calling the api endpoint with priviledge required")
    public Response privilegeEscalationCall(String token){

        return given()
                .spec(authSpec.setToken(token))
                .when()
                .get("/auth/admin-ping")
                .then()
                .extract()
                .response();

    }

    @Step("Search for available flights")
    public Response searchFlight(){
        return given()
                .spec(configSpec.setHeaders())
                .queryParam("from","BLR")
                .queryParam("to","GOI")
                .queryParam("date","2026-07-17")
                .when()
                .get("/flights")
                .then()
                .extract()
                .response();
    }

    @Step("Search for seats available in a flights")
    public String getFlightSeats(String flightid){
        return given()
                .spec(configSpec.setHeaders())
                .pathParam("flight_id",flightid)
                .when()
                .get("/flights/{flight_id}/seats")
                .then()
                .extract()
                .path("rows.seats.flatten().find { !it.occupied }.seat_id");
    }

    @Step("Booking a seat in flight")
    public String bookSeats(String token,String flightid,String seatid){
        return given()
                .spec(authSpec.setToken(token))
                .body(Map.of("journeyType","flight",
                        "inventoryId",flightid,
                        "seatIds", Arrays.asList(seatid)
                        ))
                .when()
                .post("/bookings")
                .then()
                .extract().path("id");
    }

    @Step("Paying for booked seats")
    public Response payForSeats(String token,String bookingid){
        return given()
                .spec(authSpec.setToken(token))
                .pathParam("id",bookingid)
                .when()
                .post("/bookings/{id}/pay")
                .then()
                .extract().response();
    }

    @Step("Confirming the paid seats")
    public Response confirmSeats(String token,String bookingid){
        return given()
                .spec(authSpec.setToken(token))
                .pathParam("id",bookingid)
                .when()
                .post("/bookings/{id}/confirm")
                .then()
                .extract().response();
    }


    @Step("Cancelling the booked seats")
    public Response cancelSeats(String token,String bookingid){
        return given()
                .spec(authSpec.setToken(token))
                .pathParam("id",bookingid)
                .when()
                .post("/bookings/{id}/cancel")
                .then()
                .extract().response();
    }

}
