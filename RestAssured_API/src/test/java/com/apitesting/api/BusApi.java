package com.apitesting.api;

import com.apitesting.data.model.ApiModels.BusSearchResponse;
import com.apitesting.data.model.ApiModels.BusSeatMap;
import com.apitesting.support.specifications.RequestSpecifications;
import com.apitesting.support.specifications.ResponseSpecifications;

import static io.restassured.RestAssured.given;


public class BusApi {
    public BusSearchResponse search(String token, String origin, String destination, String travelDate) {
        return given().spec(RequestSpecifications.authenticatedRequest(token)).queryParam("from", origin).queryParam("to", destination)
                .queryParam("date", travelDate).when().get("/buses")
                .then().spec(ResponseSpecifications.ok()).extract().as(BusSearchResponse.class);
    }

    public BusSeatMap seatMap(String token, String busId) {
        return given().spec(RequestSpecifications.authenticatedRequest(token)).when().get("/buses/{id}/seats", busId)
                .then().spec(ResponseSpecifications.ok()).extract().as(BusSeatMap.class);
    }
}
