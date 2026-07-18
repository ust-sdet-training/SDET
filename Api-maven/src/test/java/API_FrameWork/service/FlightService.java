package API_FrameWork.service;

import API_FrameWork.Factory.SpecFactory;
import API_FrameWork.config.EndPoints;
import API_FrameWork.models.FlightResponse;

import static io.restassured.RestAssured.given;

public class FlightService {
    public FlightResponse searchFlights(
            String from,
            String to,
            String date,
            String travelClass) {
        return given()
                .spec(SpecFactory.requestSpec())
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("date", date)
                .queryParam("class", travelClass)
                .when()
                .get(EndPoints.SEARCH_FLIGHTS)
                .then()
                .statusCode(200)
                .extract()
                .as(FlightResponse.class);
    }
}