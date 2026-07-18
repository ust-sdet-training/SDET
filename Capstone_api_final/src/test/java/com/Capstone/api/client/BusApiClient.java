package com.Capstone.api.client;
import com.Capstone.api.model.modelData.BusSearchResponse;
import com.Capstone.api.model.modelData.BusSeatMap;
import com.Capstone.api.support.SpecFactory.reqSpec;
import com.Capstone.api.support.SpecFactory.respSpec;

import static io.restassured.RestAssured.given;
public class BusApiClient {
    public BusSearchResponse search(String token, String from, String to, String date) {
        return given()
                .spec(reqSpec.authRequest(token))
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("date", date)
                .get("/buses")
                .then()
                .spec(respSpec.okSuccess())
                .extract().as(BusSearchResponse.class);
    }
    public BusSeatMap seatMap(String token, String busId) {
        return given()
                .spec(reqSpec.authRequest(token))
                .get("/buses/{id}/seats", busId)
                .then()
                .spec(respSpec.okSuccess())
                .extract().as(BusSeatMap.class);
    }
}