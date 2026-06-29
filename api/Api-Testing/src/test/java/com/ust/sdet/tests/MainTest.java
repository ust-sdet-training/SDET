package com.ust.sdet.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class MainTest {

    @Test
    @DisplayName("Create a new post")
    void createANewPost(){




        Map<String, Object> payload = Map.ofEntries(
                Map.entry("firstname", "John"),
                Map.entry("lastname", "David"),
                Map.entry("totalprice", 250),
                Map.entry("depositpaid", true),
                Map.entry("bookingdates",
                        Map.ofEntries(
                                Map.entry("checkin", "2026-08-10"),
                                Map.entry("checkout", "2026-08-15")
                        )
                ),
                Map.entry("additionalneeds", "Breakfast")
        );








        Response response =
                given()
                        .spec(Spec.setup())
                        .body(payload)
                        .when()
                        .post("/")
                        .then()
                        .extract().response();

        System.out.println(response.asPrettyString());





    }

    @Test
    @DisplayName("Get a exisitng post")
    void getAPost(){

        Map<String, Object> payload = Map.ofEntries(
                Map.entry("firstname", "John"),
                Map.entry("lastname", "David"),
                Map.entry("totalprice", 250),
                Map.entry("depositpaid", true),
                Map.entry("bookingdates",
                        Map.ofEntries(
                                Map.entry("checkin", "2026-08-10"),
                                Map.entry("checkout", "2026-08-15")
                        )
                ),
                Map.entry("additionalneeds", "Breakfast")
        );



        String id =
                given()
                        .spec(Spec.setup())
                        .body(payload)
                        .when()
                        .post("/")
                        .then()
                        .extract().path("bookingid");











        Response response =
                given()
                        .spec(Spec.setup())
                        .pathParam("id",id)
                        .when()
                        .get("/booking/{id}")
                        .then()
                        .log().all()
                        .extract().response();

        System.out.println(response.asPrettyString());





    }

}
