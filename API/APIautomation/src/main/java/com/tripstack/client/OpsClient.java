package com.tripstack.client;

import com.tripstack.config.ConfigManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OpsClient {

    private final String BASE_URL =
            ConfigManager.BASE_URL;

    public Response resetNamespace(
            String token
    ) {

        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header(
                        "Authorization",
                        "Bearer " + token
                )
                .body("{}")
                .log().all()
                .when()
                .post("/reset")
                .then()
                .log().all()
                .extract()
                .response();
    }

    public Response resetAnotherNamespace(
            String token,
            String empId
    ) {

        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header(
                        "Authorization",
                        "Bearer " + token
                )
                .body("{}")
                .log().all()
                .when()
                .post("/reset?emp=" + empId)
                .then()
                .log().all()
                .extract()
                .response();
    }
}