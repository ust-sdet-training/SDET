package com.Capstone.api.client;

import com.Capstone.api.support.SpecFactory.reqSpec;
import com.Capstone.api.support.SpecFactory.respSpec;
import static io.restassured.RestAssured.given;
public class ResetApi {
    public void reset1(String token) {
        given().spec(reqSpec.authRequest(token)).body("{}")
                .when().post("/reset")
                .then().spec(respSpec.okSuccess());
    }
}
