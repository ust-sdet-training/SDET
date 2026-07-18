package API_FrameWork.service;

import API_FrameWork.Factory.SpecFactory;
import API_FrameWork.config.EndPoints;
import API_FrameWork.models.SeatMapResponse;

import static io.restassured.RestAssured.given;

public class SeatService {
    public SeatMapResponse getSeatMap(String flightId) {
        return given()
                .spec(SpecFactory.requestSpec())
                .when()
                .get(EndPoints.SEAT_MAP.replace("{id}", flightId))
                .then()
                .statusCode(200)
                .extract()
                .as(SeatMapResponse.class);
    }
}