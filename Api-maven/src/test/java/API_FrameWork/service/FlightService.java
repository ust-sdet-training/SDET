package API_FrameWork.service;

import API_FrameWork.Factory.RequestSpecFactory;
import API_FrameWork.config.EndPoints;
import API_FrameWork.models.FlightResponse;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class FlightService {

    public FlightResponse searchFlights(
            String from,
            String to,
            String date,
            String flightClass) {

        Response response =
                given()
                        .spec(RequestSpecFactory.getRequestSpec())
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .queryParam("date", date)
                        .queryParam("class", flightClass)
                        .when()
                        .get(EndPoints.FLIGHTS)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        return response.as(FlightResponse.class);
    }
}