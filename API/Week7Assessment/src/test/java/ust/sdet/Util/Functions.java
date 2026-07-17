package ust.sdet.Util;

import ust.sdet.Data.TestDataBuilder;
import ust.sdet.SpecFactory.ConfigSpec;

import static io.restassured.RestAssured.given;

public class Functions {

    ConfigSpec configSpec = new ConfigSpec();

    TestDataBuilder testDataBuilder = new TestDataBuilder();

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

}
