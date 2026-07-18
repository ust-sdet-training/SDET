package ust.sdet.Util;

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

    public Response privilegeEscalationCall(String token){

        return given()
                .spec(authSpec.setToken(token))
                .when()
                .get("/auth/admin-ping")
                .then()
                .extract()
                .response();

    }

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

    public Response payForSeats(String token,String bookingid){
        return given()
                .spec(authSpec.setToken(token))
                .pathParam("id",bookingid)
                .when()
                .post("/bookings/{id}/pay")
                .then()
                .extract().response();
    }

    public Response confirmSeats(String token,String bookingid){
        return given()
                .spec(authSpec.setToken(token))
                .pathParam("id",bookingid)
                .when()
                .post("/bookings/{id}/confirm")
                .then()
                .extract().response();
    }

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
